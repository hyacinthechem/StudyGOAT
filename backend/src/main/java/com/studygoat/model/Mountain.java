package main.java.com.studygoat.model;

public enum Mountain {
    MOUNTRUAPEHU("Mount Ruapehu", 30),
    GLACIERPEAK("Glacer Peak", 100),  //CHECK FOR NO MOUNT VULNERABILITY
    MOUNTBLANC("Mount Blanc",600),
    MOUNTEVEREST("Mount Everest", 2500);

    private final String name;
    private final int requiredHours;

     Mountain(String name, int requiredHours){
        this.name = name;
        this.requiredHours = requiredHours;
    }

    public String getMountainName(){
         return name;
    }

    public int getRequiredHours(){
         return requiredHours;
    }

}
