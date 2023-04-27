package dev.nipafx.livefx.markdown;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleMarkTests {

	private final SimpleMark mark = new SimpleMark();

	@Test
	void emptyLine_emptyParagraph() {
		var parsed = mark.parse("");

		assertThat(parsed).isEqualTo("<p></p>");
	}

	@Nested
	class Html {

		@Test
		void containsOnLoad_removed() {
			var parsed = mark.parse("<body onload=alert('Alert!')>This alerts on load.</body>");

			assertThat(parsed).isEqualTo("<p>This alerts on load.</p>");
		}

		@Test
		void containsScriptTag_removed() {
			var parsed = mark.parse("This has a script tag. <script>alert('Alert!')</script>");

			assertThat(parsed).isEqualTo("<p>This has a script tag.</p>");
		}

		@Test
		void containsDiv_removed() {
			var parsed = mark.parse("<div>This is in a div.</div>");

			assertThat(parsed).isEqualTo("<p>This is in a div.</p>");
		}

		@Test
		void containsAnchor_removed() {
			var parsed = mark.parse("This is <a href=\"https://evilcorp.com\">a link</a>.");

			assertThat(parsed).isEqualTo("<p>This is a link.</p>");
		}

		@Test
		void containsUrl_remains() {
			var parsed = mark.parse("This is a URL: https://evilcorp.com");

			assertThat(parsed).isEqualTo("<p>This is a URL: https://evilcorp.com</p>");
		}

	}

	@Nested
	class SingleLine {

		@Test
		void withoutMarkup_inParagraph() {
			var parsed = mark.parse("Simple text without markup");

			assertThat(parsed).isEqualTo("<p>Simple text without markup</p>");
		}

		@Test
		void withBoldCharacter_bolded() {
			var parsed = mark.parse("Text with *a* bold character");

			assertThat(parsed).isEqualTo("<p>Text with <b>a</b> bold character</p>");
		}

		@Test
		void withBoldText_bolded() {
			var parsed = mark.parse("Text with some *bold* text");

			assertThat(parsed).isEqualTo("<p>Text with some <b>bold</b> text</p>");
		}

		@Test
		void withAsteriskSurroundedBySpaces_unchanged_1() {
			var parsed = mark.parse("Text with almost * bold* text");

			assertThat(parsed).isEqualTo("<p>Text with almost * bold* text</p>");
		}

		@Test
		void withAsteriskSurroundedBySpaces_unchanged_2() {
			var parsed = mark.parse("Text with almost *bold * text");

			assertThat(parsed).isEqualTo("<p>Text with almost *bold * text</p>");
		}

		@Test
		void withAsteriskInWord_unchanged_1() {
			var parsed = mark.parse("Text with al*most bold* text");

			assertThat(parsed).isEqualTo("<p>Text with al*most bold* text</p>");
		}

		@Test
		void withAsteriskInWord_unchanged_2() {
			var parsed = mark.parse("Text with *almost bol*d text");

			assertThat(parsed).isEqualTo("<p>Text with *almost bol*d text</p>");
		}

		@Test
		void withItalicCharacter_italicized() {
			var parsed = mark.parse("Text with _a_ [sic] italic character");

			assertThat(parsed).isEqualTo("<p>Text with <i>a</i> [sic] italic character</p>");
		}

		@Test
		void withItalicText_italicized() {
			var parsed = mark.parse("Text with some _italic_ text");

			assertThat(parsed).isEqualTo("<p>Text with some <i>italic</i> text</p>");
		}

		@Test
		void withUnderscoreSurroundedBySpace_unchanged_1() {
			var parsed = mark.parse("Text with almost _ italic_ text");

			assertThat(parsed).isEqualTo("<p>Text with almost _ italic_ text</p>");
		}

		@Test
		void withUnderscoreSurroundedBySpace_unchanged_2() {
			var parsed = mark.parse("Text with almost _italic _ text");

			assertThat(parsed).isEqualTo("<p>Text with almost _italic _ text</p>");
		}

		@Test
		void withUnderscoreInWord_unchanged_1() {
			var parsed = mark.parse("Text with al_most italic_ text");

			assertThat(parsed).isEqualTo("<p>Text with al_most italic_ text</p>");
		}

		@Test
		void withUnderscoreInWord_unchanged_2() {
			var parsed = mark.parse("Text with _almost itali_c text");

			assertThat(parsed).isEqualTo("<p>Text with _almost itali_c text</p>");
		}

	}

}