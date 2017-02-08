import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;

public class MyAnalyzer extends Analyzer {
  @Override
   protected TokenStreamComponents createComponents(final String fieldName) {
    final StandardTokenizer src = new StandardTokenizer();
    src.setMaxTokenLength(255);
    TokenStream tok = new StandardFilter(src);
    tok = new LowerCaseFilter(tok);
    tok = new StopFilter(tok, StopAnalyzer.ENGLISH_STOP_WORDS_SET);
    //Adding the StemFilter here
    tok = new PorterStemFilter(tok);
    return new TokenStreamComponents(src, tok) {
      @Override
      protected void setReader(final Reader reader) {
        src.setMaxTokenLength(255);
        super.setReader(reader);
      
      }
    };
   }
   @Override
   protected TokenStream normalize(String fieldName, TokenStream in) {
     // Assuming FooFilter is about normalization and BarFilter is about
     // stemming, only FooFilter should be applied
     TokenStream result = new StandardFilter(in);
     result = new LowerCaseFilter(result);
     result = new StopFilter(result, StopAnalyzer.ENGLISH_STOP_WORDS_SET);
    //Adding the StemFilter here
     result = new PorterStemFilter(result);
     return result;
    }
}
