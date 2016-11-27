package org.ehfg.app.twitter.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.ehfg.app.twitter.data.Tweet;
import org.ehfg.app.twitter.data.TwitterUser;
import org.ehfg.app.twitter.service.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.joining;

/**
 * @author patrick
 * @since 11.2016
 */
@Service
public class SearchServiceImpl implements SearchService {
	private static final String FIELD_AUTHOR_NAME = "author.name";
	private static final String FIELD_AUTHOR_NICKNAME = "author.nickname";
	private static final String FIELD_AUTHOR_IMAGE = "author.profileImage";
	private static final String FIELD_TIMESTAMP = "timestamp";
	private static final String FIELD_RETWEET = "retweet";
	private static final String FIELD_RETWEETED_BY = "retweet.by";
	private static final String FIELD_ID = "id";
	private static final String FIELD_CONTENT = "content";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final TwitterService twitterService;

	private Directory index = new RAMDirectory();

	@Autowired
	public SearchServiceImpl(TwitterService twitterService) {
		this.twitterService = twitterService;
	}

	@Override
	public SearchResult find(String input, int maxResults) {
		logger.info("searching for [{}]", input);
		checkNotNull(input, "input must not be null");

		try {
			DirectoryReader directoryReader = StandardDirectoryReader.open(index);
			IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

			final SearchResult result = new SearchResult();

			TopDocs search = indexSearcher.search(new TermQuery(new Term(FIELD_CONTENT, input)), maxResults);
			logger.debug("found [{}] results for input [{}]", search.scoreDocs.length, input);
			for (ScoreDoc scoreDoc : search.scoreDocs) {
				Document doc = indexSearcher.doc(scoreDoc.doc);
				result.addItem(buildTweet(doc));
			}

			return result;
		} catch (IOException e) {
			logger.error("this IOException should never happen, we are not doing any I/O", e);
			return new SearchResult();
		}
	}

	@Override
	public boolean refreshIndex() {
		logger.info("updating index...");
		try {
			if (index != null) {
				index.close();
			}

			index = new RAMDirectory();
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
			try (IndexWriter indexWriter = new IndexWriter(index, indexWriterConfig)) {
				logger.info("building index for sessions...");
				for (Tweet tweet : twitterService.findForIndex()) {
					logger.trace("building index for tweet [{}]", tweet);
					indexWriter.addDocument(buildDocument(tweet));
				}
			}

			return true;
		} catch (IOException e) {
			logger.error("this IOException should never happen, we are not doing any I/O", e);
			return false;
		}

	}

	private Document buildDocument(Tweet tweet) {
		Document doc = new Document();
		doc.add(new StringField(FIELD_ID, tweet.getId(), Field.Store.YES));
		doc.add(new TextField(FIELD_CONTENT, tweet.getMessage(), Field.Store.YES));
		doc.add(new StringField(FIELD_AUTHOR_NAME, tweet.getFullName(), Field.Store.YES));
		doc.add(new StringField(FIELD_AUTHOR_NICKNAME, tweet.getNickName(), Field.Store.YES));
		doc.add(new StringField(FIELD_AUTHOR_IMAGE, tweet.getProfileImage(), Field.Store.YES));
		doc.add(new StringField(FIELD_TIMESTAMP, tweet.getTimestamp().toString(), Field.Store.YES));
		doc.add(new StringField(FIELD_RETWEET, Boolean.toString(tweet.isRetweet()), Field.Store.YES));
		doc.add(new StoredField(FIELD_RETWEETED_BY, tweet.getRetweetedBy().stream().collect(joining(","))));
		return doc;
	}

	private Tweet buildTweet(Document doc) {
		TwitterUser author = new TwitterUser();
		author.setFullName(doc.get(FIELD_AUTHOR_NAME));
		author.setNickName(doc.get(FIELD_AUTHOR_NICKNAME));
		author.setProfileImage(doc.get(FIELD_AUTHOR_IMAGE));

		Tweet result = new Tweet();
		result.setId(doc.get(FIELD_ID));
		result.setCreationDate(LocalDateTime.parse(doc.get(FIELD_TIMESTAMP)));
		result.setRetweet(Boolean.parseBoolean(doc.get(FIELD_RETWEET)));
		result.setAuthor(author);
		result.setMessage(doc.get(FIELD_CONTENT));

		for (String userName : doc.get(FIELD_RETWEETED_BY).split(",")) {
			result.addRetweet(userName);
		}

		return result;
	}
}
