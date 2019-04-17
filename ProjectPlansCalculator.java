import java.io.*;
import java.util.*;
import java.text.*;
public class ProjectPlansCalculator {
	static int taskCtr;
	static String projTitle = "";
	static ArrayList<String> projTask = new ArrayList<String>();
	static ArrayList<Integer> projDays = new ArrayList<Integer>();
	static ArrayList<Integer> projIDS = new ArrayList<Integer>();
	static ArrayList<ArrayList<String>> projDates1 = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<Integer>> projDependency = new ArrayList<ArrayList<Integer>>();
	static ArrayList<ArrayList<String>> projDates = new ArrayList<ArrayList<String>>();
	static BufferedReader input = new BufferedReader( new InputStreamReader(System.in));
	static SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd");
	 static Calendar c = Calendar.getInstance();

//Main-------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public static void main(String[] args) throws IOException {
		String input1;
		Random rand = new Random();
		int input2 = 0, op = 0;
		while(true) {
			op = 0;
			System.out.println("\n\nEnter [1] to start building project");
			input1 = input.readLine();
			try {
				input2 = Integer.parseInt(input1);
			}catch(Exception e) {
				op = 1;
			}
			
			if(op == 0 && input2 == 1) {
				taskCtr = 0;
				buildProject();
				break;
			}else {
				System.out.println("Invalid Input, Try Again");

			}
		}
	    

	}

//Assign Tasks-----------------------------------------------------------------------------------------------------------------------------------------------------------------

	private static void buildProject() throws IOException {
		String input1;
		int input2 = 0;
		System.out.println("Project Title:\n");
		input1 = input.readLine();
		projTitle = input1;
		System.out.println("Project initiation task:\n");
		
		System.out.println("TASK TITLE:");
		input1 = input.readLine();
		projTask.add(input1);
		
		System.out.println("TASK DURATION(DAYS):");
		while(true) {
			input1 = input.readLine();
			int op2 = 0;
			try {
				input2 = Integer.parseInt(input1);
			}catch(Exception e) {
				op2 = 1;
			}
			
			if(op2 == 1) {
				System.out.print(" - Invalid Input, Try again\n");
			}else {
				projDays.add(input2);
				break;
			}
				
		}
		
		ArrayList<Integer> catchDep = new ArrayList<Integer>();
		catchDep.add(0);
		projDependency.add(catchDep);
		projIDS.add(taskCtr);
		taskCtr++;
		
		printTasks();
		
		while(true) {
			int op1 = 0;
			System.out.println("\n\nEnter your choice:\n\n"
					+ "[1] - Add Task							l\n"
					+ "[2] - Calculate Project Life Cycle");
			input1 = input.readLine();
			try {
				input2 = Integer.parseInt(input1);
			}catch(Exception e){
				op1 = 1;
			}
			
			if(op1 == 0) {
				if(input2 == 1) {
					addTask();
					printTasks();
				}else if(input2 == 2) {
					calTask();
				}else {
					System.out.print(" - Invalid Input, Try again\n");
				}
			}else {
				System.out.print(" - Invalid Input, Try again\n");
			}
			
		}
		
	}

//Print Tasks------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private static void printTasks() {
		System.out.println("\n\n"
				+ "Task - ID		Duration		Task			Dependency\n"
				+ "---------		-----------		------------	-----------\n");
		for(int h = 0; h < taskCtr; h++) {
			ArrayList<Integer> catchDep = projDependency.get(h);
			String dep = "";
			for(int g = 0; g < catchDep.size(); g++) {
				if(catchDep.get(g) != 0) {
					dep += catchDep.get(g) + " ";
				}else {
					dep = "";
					break;
				}
				
			}
			System.out.print(h+100 + "			" + projDays.get(h) + "			" + projTask.get(h) + "		" + dep + "\n");
		}
	}

//Add Task---------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private static void addTask() throws IOException  {
		String input1;
		int input2 = 0;
		System.out.println("\n\nAdd Task:\n");
		
		System.out.println("TASK TITLE:");
		input1 = input.readLine();
		projTask.add(input1);
		
		System.out.println("TASK DURATION(DAYS):");
		while(true) {
			input1 = input.readLine();
			int op2 = 0;
			try {
				input2 = Integer.parseInt(input1);
			}catch(Exception e) {
				op2 = 1;
			}
			
			if(op2 == 1) {
				System.out.print(" - Invalid Input, Try again\n");
			}else {
				projDays.add(input2);
				break;
			}
				
		}
		
		ArrayList<Integer> catchDep = new ArrayList<Integer>();
		System.out.println("Enter ID where the task is dependent OR Enter [0] to stop adding dependency:");
		while(true) {
			input1 = input.readLine();
		if(input1.equals("0")) {
			if(catchDep.size() == 0) {
				catchDep.add(0);
			}
			Collections.sort(catchDep);
			projDependency.add(catchDep);

			projIDS.add(taskCtr);
			taskCtr++;
			break;
		}else{
			int op = 0;
			try {
				input2 = Integer.parseInt(input1);
			}catch(Exception e) {
				op = 1;
			}
			if(op == 0) {
				int op2 = 0;
				for(int h = 0; h < taskCtr; h++) {
					if(input2 == h + 100) {
						op2 = 1;
						break;
					}
				}
				if(op2 == 1) {
					if(catchDep.contains(input2) == false) {
						catchDep.add(input2);
					System.out.print(" - Dependency Added\n");
					}else {
						System.out.print(" - ID is already a dependency\n");
					}
					
				}else {
					System.out.print(" - ID does not exists, Not Added\n");
				}
			}else {
				System.out.print(" - Invalid Input\n");
			}
		}
		}
		
		
	}
	
//Calculate task---------------------------------------------------------------------------------------------------------------------------------------------------------------

	private static void calTask() throws IOException  {
		String startD = "";
		
		System.out.println("\nEnter you starting date (Ex. Jul-05, note that it should in the right format):");
		while(true) {
			startD = input.readLine();
		
		try{
			
			   c.setTime(sdf.parse(startD));
			   break;
			}catch(ParseException e){
				System.out.print(" - Invalid Input, try again\n");
			 }
		
		
		}
		
		for(int j = 0; j < projDays.size(); j++) {
			ArrayList<Integer> catchDep = projDependency.get(j);
			int dep = catchDep.get(catchDep.size()-1);
			
			if(dep == 0) {
				String start = "", end = "";
				try {
					c.setTime(sdf.parse(startD));
					start = sdf.format(c.getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.add(Calendar.DAY_OF_MONTH, projDays.get(j));
				end = sdf.format(c.getTime());
				ArrayList<String> dates = new ArrayList<String>();
				dates.add(start);
				dates.add(end);
				projDates.add(dates);
			}else {
				int dep2 = dep - 100;
				String start = "", end = "";
				ArrayList<String> depDates = projDates.get(dep2);
				try {
					c.setTime(sdf.parse(depDates.get(1)));
					start = sdf.format(c.getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.add(Calendar.DAY_OF_MONTH, projDays.get(j));
				end = sdf.format(c.getTime());
				ArrayList<String> dates = new ArrayList<String>();
				dates.add(start);
				dates.add(end);
				projDates.add(dates);
						
			}
		}
		sortTasks();
		showFinalDates();
		
		
		
		
	}

//Sort tasks according to the dates--------------------------------------------------------------------------------------------------------------------------------------------
	
	private static void sortTasks() {
		 projDates1 = projDates;
		Date date1 = null, date2 = null, date3 = null, date4 = null;
		for(int m = 0; m < projDates1.size(); m++) {
			for(int n = m + 1; n < projDates1.size(); n++) {
				ArrayList<String> dates1 = projDates1.get(m);
				ArrayList<String> dates2 = projDates1.get(n);
				
				try {
					date1 = sdf.parse(dates1.get(0));
					date2 = sdf.parse(dates2.get(0));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(date1.after(date2)) {
					Collections.swap(projDates1, m, n);
					Collections.swap(projIDS, m, n);
				}else if(date1.equals(date2)) {
					try {
						date3 = sdf.parse(dates1.get(1));
						date4 = sdf.parse(dates2.get(1));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(date3.after(date4)) {
						Collections.swap(projDates1, m, n);
						Collections.swap(projIDS, m, n);
					}
				}
			}
		}
	}

//Show calculated project cycle------------------------------------------------------------------------------------------------------------------------------------------------

	private static void showFinalDates() throws IOException {
		ArrayList<String> startD = projDates1.get(0);
		ArrayList<String> endD = projDates1.get(projDates1.size()-1);
		
		String startDate = startD.get(0);
		String endDate = endD.get(1);

		System.out.println("\n\n"
				+ "# Project Plan for " + projTitle + "\n"
				+ "# Target Start Date: " + startDate + "\n"
				+ "# Target Completion Date: " + endDate + "\n\n"
				+ "# Project Tasks\n"
				+ "Task - ID		Start Date		End Date		Duration		Task			Dependency\n"
				+ "---------		-----------		------------	-----------		-----------		--------------\n");
		for(int h = 0; h < projIDS.size(); h++) {
			int id = projIDS.get(h);
			ArrayList<String> datess = projDates1.get(h);
			ArrayList<Integer> catchDep = projDependency.get(id);
			String dep = "";
			for(int g = 0; g < catchDep.size(); g++) {
				if(catchDep.get(g) != 0) {
					dep += catchDep.get(g) + " ";
				}else {
					dep = "";
					break;
				}
				
			}
			System.out.print(id+100 + "			" + datess.get(0) + "			" + datess.get(1) + "			" + projDays.get(id) + "			" + projTask.get(id) + "		" + dep + "\n");
		}
		input.readLine();
	
	}

}
