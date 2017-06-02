package org.ehfg.app.program.data.db;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author poberbichler
 * @since 12.2016
 */
public class EscapeUtilsTest {
	@Test
	public void shouldEscapeText() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource("session.txt");
		String fileInput = new String(Files.readAllBytes(classPathResource.getFile().toPath()));

		String outputText = EscapeUtils.escapeText(fileInput);

		assertThat(outputText).doesNotContain("font-family");

		// links should be rendered this way
		assertThat(outputText).containsPattern("<a href=\"#\".*onclick=\"window.open\\(");
	}

}