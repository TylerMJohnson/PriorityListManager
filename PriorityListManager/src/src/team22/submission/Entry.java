package src.team22.submission;

import java.io.Serializable;
import java.util.Date;

public class Entry implements Serializable {
	
	private String description;
	private int priority;
	private String dueDate, started, finished;

	private String status;

	public Entry(int priority, String description, String dueDate, String status, String started, String finished) {
		this.priority = priority;
		this.description = description;
		this.dueDate = dueDate;
		this.status = status;
		this.started = started;
		this.finished = finished;
	}

	public Entry() {

	}

	public String getDescription() {
		return description;
	}

	public String getDueDate() {
		return dueDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setStatus(String string) {
		this.status = string;
	}
	
	public String getStarted() {
		return started;
	}

	public void setStarted(String started) {
		this.started = started;
	}
	
	public String getFinished() {
		return finished;
	}
	
	public void setFinished(String finished) {
		this.finished = finished;
	}
	
	public String toString(){
		return priority + ":" + description + ":" + dueDate + ":" + status + ":" + started + ":" + finished;
	}
	
	public String printLine() {
		String result = "";
		if(this.getStatus() == "incomplete") {
			result += "TASK INCOMPLETE:  ";
		}
		else {
			result += "TASK COMPLETE:  ";
		}
		result += "\n";
		result += "PRIO:  "  + this.getPriority() + "\t";
		result += "DESC:  " + this.getDescription() + "\t";
		result += "\n";
		result += "DUE:  " + this.getDueDate() + "\t";
		result += "START: " + this.getStarted() + "\t";
		if(this.getFinished() == "NA") {
		}
		else {
			result += "FINISH:  " + this.getFinished();
		}
		result += "\n";
		return result;
	}

	public String getStatus() {
		return status;
	}

	
}
