import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main { //Main

    public static void main(String[] args) {

        String sTitle = JOptionPane.showInputDialog("Please Enter Title"); //ask user for title
        if (!sTitle.equals("") ) { //If user enters in a custom title
            Survey gui = new Survey(sTitle); //pass in title entered
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //give JFrame parameters for closing
            gui.setSize(325, 450); //set size
            gui.setLocationRelativeTo(null); //set to center of the screen
            gui.setVisible(true); //display the JFrame
            gui.pack();
        } else  { //If no title is entered
            Survey gui = new Survey(); //create new instance with no parameters
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //give JFrame parameters for closing
            gui.setSize(325, 530); //set size
            gui.setLocationRelativeTo(null); //set to center of the screen
            gui.setVisible(true); //display the JFrame
            gui.pack();
        }
    }
}

class Survey extends JFrame{ // survey class
static int userNumber = 0;  //Here I create all private variables, arrays for JFrame, and elements.
String surveyTitle = "";
private JLabel lTitle;
private JLabel lUserNumber;
private JButton Show;
private JButton user;
private JButton submit;
private JButton stats;
private JDialog resultsD;
private JComboBox[] CBrating = new JComboBox[1]; //Combobox array to easily reproduce boxes for number of questions
private int numQ;
String[] questions = new String[10];
String[] aRating = {"1 - Poor", "2", "3 - Average", "4", "5 - Great" }; //Combobox choices
String[] choices = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}; //# of question choices in dialogbox

public Survey(){ //Default no parameter constructor
    super("Customer Survey"); //set frame title because user didnt insert custom
    userNumber = 0; //initialize
    surveyTitle = "Customer Survey"; //pass in title
    enterNumQ(); //Call method to ask user how many questions to enter/display
    enterQuestions(); //call method that asks what the questions are
    setGUI(surveyTitle); //display JFrame
}
public Survey(String title){ //constructor used after user enters title
    super(title); //set window's title to entered title
    userNumber = 0; //initialize
    enterNumQ();//Call method to ask user how many questions to enter/display
    enterQuestions(); //call method that asks what the questions are
    setGUI(title); //Display JFrame
}

    public void enterNumQ(){  //Show dialog to ask how many questions user wishes to input
        String ans = (String) JOptionPane.showInputDialog(null, "How many questions do you wish to input?", "Questions", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
        setNumQ(Integer.parseInt(ans));
    }

    public void enterQuestions(){ //show dialog to ask what the questions are.
        for(int f = 0; f < getNumQ(); f++ ){ //loop through array inserting question strings into array
            questions[f] = JOptionPane.showInputDialog(null, "Enter Question #" + (f + 1));
        }
    }
    public  void setNumQ(int i){
        numQ = i;
    }  //encap
    public int getNumQ(){
        return numQ;
    } //encap
    public void setGUI(String title){ //creating actual JFrame

        JPanel masterPanel = new JPanel(new BorderLayout(10, 10)); //Create new master JPanel
        JPanel qPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        JPanel aPanel = new JPanel(new GridLayout(0,1,10, 10));
        JPanel bPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        CBrating = new JComboBox[getNumQ()]; //initialize combobox array
        lTitle = new JLabel();
        lUserNumber = new JLabel();
        Show = new JButton("See Results");
        user = new JButton("New User");
        submit = new JButton("Submit Answers");
        stats = new JButton("Question Stats");
        lTitle.setHorizontalAlignment(SwingConstants.CENTER); //centering labels
        lUserNumber.setHorizontalAlignment(SwingConstants.CENTER);
        masterPanel.add(lTitle, BorderLayout.NORTH);
        masterPanel.add(qPanel, BorderLayout.WEST); //adding elements to JPanel
        masterPanel.add(aPanel, BorderLayout.EAST);
        bPanel.add(user);
        bPanel.add(submit); //adding all buttons
        bPanel.add(Show);
        bPanel.add(stats);
        masterPanel.add(bPanel, BorderLayout.SOUTH);
        this.getContentPane().add(masterPanel); //set master panel as this.setLayout
        generateRespondentID(); //call method that increments user number
        lTitle.setText(title); //set Title Label to entered title
        lUserNumber.setText(String.format("%d", userNumber)); //display user number
        int[][] questionAnswer = new int[10][getNumQ()]; //initialize 2d array for questions and answers to them

        for(int i = 0; i < getNumQ();i++){ //loop through questions array and create elements for each.
            qPanel.add(new Label(questions[i])); //create a label with every question
            CBrating[i] = new JComboBox(aRating); //create a combobox for each question
            aPanel.add(CBrating[i]); //add all comboboxes
        }

        submit.addActionListener(new ActionListener() { //listen for submit button click
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int row = 0; row < userNumber; row++){
                    for(int col = 0; col < getNumQ(); col++){
                        if(questionAnswer[row][col] == 0) {
                            questionAnswer[row][col] = (CBrating[col].getSelectedIndex() + 1); //Loop through each combobox and save each into array
                            CBrating[col].setEnabled(false); //disable after submitting, and keep disabled till new user clicked.
                        }
                    }
                }
            }
        });
        Show.addActionListener(new ActionListener() { //Listens for click
            @Override
            public void actionPerformed(ActionEvent e) {

                displaySurveyResults(title, questionAnswer, userNumber, getNumQ()); //display results dialog

            }
        });
        user.addActionListener(new ActionListener() { //listens for click
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRespondentID(); //increment user number
                for (int row = 0; row < userNumber; row++){
                    for(int col = 0; col < getNumQ(); col++){
                        if(questionAnswer[row][col] == 0) {
                            CBrating[col].setEnabled(true); //reenable comboboxes

                        }
                    }
                }
            }
        });
        stats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ans = Integer.parseInt(JOptionPane.showInputDialog("Question to display stats for?")); //ask user which question they want stats for.
                displayQuestionStats(ans, questionAnswer, userNumber); //show stats dialog
            }
        });
    }

public void displaySurveyResults(String title, int[][] questionAnswer, int userNumber, int numQ){
    lTitle.setText(title);
    resultsD = new Dialog(questionAnswer, userNumber, numQ); //calls to Dialog to show results dialog
}

public void generateRespondentID(){
    userNumber++; //increment usernumber
}

public void displayQuestionStats(int qNum, int[][] questionAnswer, int userNumber){
        resultsD = new Dialog(qNum, questionAnswer, userNumber); //calls to Dialog to show stats dialog
    }


}


