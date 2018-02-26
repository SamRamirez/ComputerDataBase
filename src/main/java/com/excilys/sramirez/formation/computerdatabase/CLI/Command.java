package main.java.com.excilys.sramirez.formation.computerdatabase.CLI;

public enum Command {

  LIST_COMPANIES ("list companies ("),
  LIST_COMPUTERS ("list computers ("),
  INFO_COMPUTER ("info computer ("),
  CREATE_COMPUTER ("create computer ("),
  UPDATE_COMPUTER ("update computer ("),
  DELETE_COMPUTER ("delete computer ("),
  COUNT_COMPUTER ("count computers ("),
  EXIT ("exit ()"),
  DEFAULT ("default");
  
  private String name = "";

  Command(String name){
    this.name = name;
  }

  public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
  
  public String toString(){
    return name;
  }
  
  public static Command fromNameToEnum(String name) {
	  final Command[] values = Command.values();
	  Command commandReturn = DEFAULT;
	  
	  for(Command command : values) {
		  if(command.getName().equals(name)) {
			  commandReturn=command;
		  }
	  }  
	  return commandReturn;
  }




}
