package bradypod.framework.agent.core.ui;

import java.util.ArrayList;
import java.util.List;

public class Row {

	private List<Column> columns;

	public Row() {
		this.columns = new ArrayList<Column>();
	}

	public void add(String... cols) {
		for (String col : cols) {
			Column column = new Column(col);
			columns.add(column);
		}
	}

	public List<Column> getColumns() {
		return columns;
	}
}
