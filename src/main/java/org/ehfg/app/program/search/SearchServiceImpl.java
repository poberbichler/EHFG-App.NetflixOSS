package org.ehfg.app.program.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.ehfg.app.program.data.output.SessionRepresentation;
import org.ehfg.app.program.data.output.SpeakerRepresentation;
import org.ehfg.app.program.service.ProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author patrick
 * @since 11.2016
 */
@Service
class SearchServiceImpl implements SearchService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final ProgramService programService;

	private Directory index = new RAMDirectory();

	@Autowired
	public SearchServiceImpl(ProgramService programService) {
		this.programService = programService;
	}

	@Override
	public SearchResult search(String input, int maxResults) {
		logger.info("searching for [{}]", input);
		checkNotNull(input, "input must not be null");

		try {
			DirectoryReader directoryReader = StandardDirectoryReader.open(index);
			IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

			final SearchResult result = new SearchResult();

			TopDocs search = indexSearcher.search(new TermQuery(new Term("content", input)), maxResults);
			logger.debug("found [{}] results for input [{}]", search.scoreDocs.length, input);
			for (ScoreDoc scoreDoc : search.scoreDocs) {
				Document doc = indexSearcher.doc(scoreDoc.doc);
				result.addItem(new SearchResultItem(doc.get("id"), ResultType.valueOf(doc.get("type")), doc.get("name")));
			}

			return result;
		} catch (IOException e) {
			logger.error("this IOException should never happen, we are not doing any I/O", e);
			return new SearchResult();
		}
	}

	@Override
	public boolean updateIndex() {
		logger.info("updating index...");
		try {
			if (index != null) {
				index.close();
			}

			index = new RAMDirectory();
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
			try (IndexWriter indexWriter = new IndexWriter(index, indexWriterConfig)) {
				logger.info("building index for sessions...");
				for (SessionRepresentation session : programService.findSessions()) {
					logger.trace("building index for session [{}]", session.getName());
					indexWriter.addDocument(buildDocument(session));
				}

				logger.info("building index for speakers...");
				for (SpeakerRepresentation speaker : programService.findSpeakers()) {
					logger.trace("building index for speaker [{}]", speaker.getFullName());
					indexWriter.addDocument(buildDocument(speaker));
				}
			}

			return true;
		} catch (IOException e) {
			logger.error("this IOException should never happen, we are not doing any I/O", e);
			return false;
		}
	}

	private Document buildDocument(SpeakerRepresentation speaker) {
		Document result = new Document();
		result.add(new TextField("content", new StringReader(speaker.getDescription())));
		result.add(new TextField("name", speaker.getFullName(), Field.Store.YES));
		result.add(new StringField("type", ResultType.SPEAKER.name(), Field.Store.YES));
		result.add(new StringField("id", speaker.getId(), Field.Store.YES));
		return result;
	}

	private Document buildDocument(SessionRepresentation session) {
		Document result = new Document();
		result.add(new TextField("content", new StringReader(session.getDescription())));
		result.add(new TextField("name", session.getName(), Field.Store.YES));
		result.add(new StringField("type", ResultType.SESSION.name(), Field.Store.YES));
		result.add(new StringField("id", session.getId(), Field.Store.YES));
		return result;
	}
}