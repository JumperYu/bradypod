package bradypod.framework.lucene;

import org.apache.lucene.search.IndexSearcher;

/**
 * 回调
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年2月14日
 */
public interface LuceneCallback<T> {
	
	public T executeQuery(IndexSearcher searcher);
	
}
