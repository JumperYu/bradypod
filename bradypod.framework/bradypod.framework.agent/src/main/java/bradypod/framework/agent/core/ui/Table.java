package bradypod.framework.agent.core.ui;

import java.util.ArrayList;
import java.util.List;

public class Table {

	private Row header = new Row();
	private List<Row> body = new ArrayList<Row>();

	public void setHeader(String... cols) {
		header.add(cols);
	}

	public void addBody(String... cols) {
		Row row = new Row();
		row.add(cols);
		body.add(row);
	}

	/**
	 * 非常烂的实现
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Column col : header.getColumns()) {
			sb.append(col.getValue()).append(TAB);
		}
		sb.append(NEXT_ROW);
		for (Row row : body) {
			for (Column col : row.getColumns()) {
				sb.append(col.getValue()).append(TAB);
			}
			sb.append(NEXT_ROW);
		}
		sb.append(NEXT_ROW);
		return sb.toString();
	}

	private static final String TAB = "\t\t";
	private static final String NEXT_ROW = "\r\n";

	public static void main(String[] args) {
		Table table = new Table();
		table.setHeader("x1", "x2", "x3", "x4");
		table.addBody("x5", "ga-command-execute-daemon", "x7", "x8");
		System.out.println(table.toString());
	}
}
