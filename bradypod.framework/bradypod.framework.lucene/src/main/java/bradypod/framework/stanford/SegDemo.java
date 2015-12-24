package bradypod.framework.stanford;

import java.io.PrintStream;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

/**
 * 斯坦福分词器测试
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月23日 下午7:28:02
 */
public class SegDemo {

	private static final String basedir = "E:/work/dev/stanford-segmenter-2015-12-09/data";

	public static void main(String[] args) throws Exception {
		System.setOut(new PrintStream(System.out, true, "utf-8"));

		Properties props = new Properties();
		props.setProperty("sighanCorporaDict", basedir);
		props.setProperty("serDictionary", basedir + "/dict-chris6.ser.gz");
		props.setProperty("inputEncoding", "UTF-8");
		props.setProperty("sighanPostProcessing", "true");

		CRFClassifier<CoreLabel> segmenter = new CRFClassifier<>(props);
		segmenter.loadClassifierNoExceptions(basedir + "/ctb.gz", props);

		String sample = "我住在美国，告诉老王。";
		List<String> segmented = segmenter.segmentString(sample);
		System.out.println(segmented);
	}

}
