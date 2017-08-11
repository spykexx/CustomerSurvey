import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Spykexx on 8/10/2017.
 */
class Dialog extends JDialog {
    private JPanel MP; //declare all panels
    private JPanel RP;
    private JPanel BP;
    private JPanel QP;
    Dialog(int[][] QArray, int userN, int qNum){ //Dialog for results
        MP = new JPanel(new BorderLayout(10, 10));
        RP = new JPanel(new GridLayout((userN + 1), qNum + 1, 10, 10)); //Initialize panels
        QP = new JPanel(new GridLayout(1, qNum, 10, 10));
        BP = new JPanel(new GridLayout(2, 1, 0, 5));
        RP.setMaximumSize(new Dimension(5, 5));
        JLabel[] qLabels = new JLabel[qNum]; //Create arrays for JLabels to dynamically display results
        JLabel uLabel = new JLabel();
        JLabel[][] results = new JLabel[userN][qNum];
        for(int l = 0; l < qNum; l++ ){ //loop through question numbers to create question labels
            qLabels[l] = new JLabel("Question" + (l + 1));
            qLabels[l].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK)); //set border
            qLabels[l].setHorizontalAlignment(SwingConstants.CENTER);
            RP.add(qLabels[l]);
        }
        uLabel.setText("Users");
        uLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        for(int i = 0; i < userN; i++){ //loop through questionsAnswers array and populate all labels with results
            for (int j = 0; j < qNum; j++){
                results[i][j] = new JLabel(String.format("%d",QArray[i][j])); //initialize array of labels for results
                results[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
                results[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                RP.add(results[i][j]);
            }
        }
        this.getContentPane().add(MP); //this.setLayout
        MP.add(RP, BorderLayout.CENTER); //add panels to master
        MP.add(uLabel, BorderLayout.WEST);
        BP.add(new Label("Question " + topRatedQuestion(QArray, qNum, userN)[1][0] + " Was the highest with " + topRatedQuestion(QArray, qNum, userN)[0][0] ));
        BP.add(new Label("Question " + lowRatedQuestion(QArray, qNum, userN)[1][0] + " Was the lowest with " + lowRatedQuestion(QArray, qNum, userN)[0][0] ));
        MP.add(BP, BorderLayout.SOUTH);
        pack(); //Resizes dialog to fit perfectly on preferred element size
        setLocationRelativeTo(null); //center
        setVisible(true); //display
        this.addFocusListener(new FocusListener() { //listens for when user takes focus off dialog
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                dispose();
            } //closes dialog on focus loss
        });

    }
    Dialog(int qNum, int[][] QArray, int userN){  //Dialog for Stats
        MP = new JPanel(new BorderLayout(10, 10)); //master panel
        this.QP = new JPanel(); //initializing panel for BoxLayout
        this.QP.setLayout(new BoxLayout(QP, BoxLayout.Y_AXIS));
        JLabel[][] qLabels = new JLabel[userN][qNum]; //init JLabel array
        JLabel labelName = new JLabel("Stats for Question " + qNum);
        JLabel usersL = new JLabel("Users");
        usersL.setVerticalAlignment(SwingConstants.CENTER);
        MP.add(usersL, BorderLayout.WEST);
        labelName.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 50));
        QP.add(labelName);
        for(int i = 0; i < userN; i++ ){ //loops through chosen question number and populates results for that question only
            for(int j = qNum - 1; j < qNum; j++){
                qLabels[i][j] = new JLabel(String.format("%d", QArray[i][j]));
                qLabels[i][j].setMinimumSize(new Dimension(10, 10));
                qLabels[i][j].setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
                QP.add(qLabels[i][j]);
            }
        }this.getContentPane().add(MP);
        MP.add(QP, BorderLayout.CENTER);

        pack(); //resize to elements
        setLocationRelativeTo(null); //center
        setVisible(true); //show
    }
    public int[][] lowRatedQuestion(int[][] questionAnswer, int numQ, int userNumber){
        int[] qAnswer = new int[numQ];
        int qTop = 0;
        int response;
        int[][] bottomResponse ={{0}, {0}};
        for(int i = 0; i < userNumber; i++){
            for(int j = 0; j < numQ; j++){
                System.out.print(questionAnswer[i][j] + " ");
                response = questionAnswer[i][j];
                qAnswer[j] = qAnswer[j] + response;

            }

        }
        bottomResponse[0][0] = numQ * 5;
        for(int m = 0; m < numQ; m++){
            //topResponse[0][0] = 0;
            if(qAnswer[m] < bottomResponse[0][0]){
                bottomResponse[0][0] = qAnswer[m];
                bottomResponse[1][0] = m + 1;
            }


        }

        return bottomResponse;
    }
    public int[][] topRatedQuestion(int[][] questionAnswer, int numQ, int userNumber){
        int[] qAnswer = new int[numQ];
        int qTop = 0;
        int response;
        int[][] topResponse ={{0}, {0}};
        for(int i = 0; i < userNumber; i++){
            for(int j = 0; j < numQ; j++){
                System.out.print(questionAnswer[i][j] + " ");
                response = questionAnswer[i][j];
                qAnswer[j] = qAnswer[j] + response;

            }

        }
        for(int m = 0; m < numQ; m++){
            //topResponse[0][0] = 0;
            if(qAnswer[m] > topResponse[0][0]){
                topResponse[0][0] = qAnswer[m];
                topResponse[1][0] = m + 1;
            }


        }return topResponse;
    }
}