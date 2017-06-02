@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7')
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient

def retrieveDataFromUrl(String url) {
	def restClient = new RESTClient(url);
	restClient.get(path: 'speakers/data/2016.rss', contentType: ContentType.TEXT) {response, reader ->
		response.headers.each { header -> println "${header.name} - ${header.value}" }

		println '============';
		return reader.text
	}
}

def readImageUrls(String data) {
	def parsedData = new XmlSlurper().parseText(data);
	return parsedData.channel.item.imgpath.collect {
		it.text().trim();
	}.toSet();
}

def downloadImages(Set<String> imageUrls) {
	def counter = 0i;
	def tempDir = File.createTempDir("aaa_speakers_", "_${new Date().format('yyyy-MM-dd')}");

	imageUrls.each { imageUrl ->
		println "getting image from ${imageUrl}"
		def url = new URL(imageUrl);

		try {
			url.withInputStream { inputStream ->
				def imageName = imageUrl.split('/');
				def file = new File(tempDir, "${imageName[imageName.length-1]}");
				println "stream to ${tempDir.absolutePath}/${file.name}"
				file << inputStream;
			}
		}

		catch (FileNotFoundException ex) {
			ex.printStackTrace()
		}
	}
}

def data = retrieveDataFromUrl('http://www.ehfg.org/feed/');
def imageUrls = readImageUrls(data);
downloadImages(imageUrls);