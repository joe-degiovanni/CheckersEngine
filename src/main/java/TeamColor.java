public enum TeamColor {

    RED("R"),
    BLACK("B");

    private String displayToken;
    TeamColor(String display){
        displayToken = display;
    }

    public String toString(){
        return displayToken;
    }
}
