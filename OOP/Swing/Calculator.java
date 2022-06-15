import javax.swing.*;
import java.awt.*;

public class Calculator extends JFrame {

    //계산기 사이즈
    public static final int WIDTH=350;
    public static final int HEIGHT=570;

    //계산기에 사용되는 전역변수들
    double firstNum;
    double secondNum;

    double savedNum1;
    double savedNum2;

    String preOperator="";
    String operator = "";

    private JPanel textPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();

    //입력된 값과 연산을 보여주는 용도
    JTextArea textAreaPreNumbers = new JTextArea();
    //입력하고 있는 숫자와 결과를 보여주는 용도
    JTextArea textAreaNumbers = new JTextArea();

    JButton buttonNum0 = new JButton();
    JButton buttonNum1 = new JButton();
    JButton buttonNum2 = new JButton();
    JButton buttonNum3 = new JButton();
    JButton buttonNum4 = new JButton();
    JButton buttonNum5 = new JButton();
    JButton buttonNum6 = new JButton();
    JButton buttonNum7 = new JButton();
    JButton buttonNum8 = new JButton();
    JButton buttonNum9 = new JButton();

    JButton buttonMultiple = new JButton();
    JButton buttonPlus = new JButton();
    JButton buttonMinus = new JButton();
    JButton buttonDot = new JButton();
    JButton buttonEqual = new JButton();

    JButton buttonDevide = new JButton();
    JButton buttonRemainder = new JButton();
    JButton buttonClearAll = new JButton();
    JButton buttonDelete = new JButton();
    JButton buttonReset = new JButton();

    JButton buttonSave1 = new JButton();
    JButton buttonSave2 = new JButton();
    JButton buttonRecall1 = new JButton();
    JButton buttonRecall2 = new JButton();
    JButton buttonClear1 = new JButton();
    JButton buttonClear2 = new JButton();

    //layout의 모양을 맞추기 위한 용도
    JButton buttonBlank1 = new JButton();
    JButton buttonBlank2 = new JButton();



    public Calculator() {

        setSize(WIDTH, HEIGHT);
        setTitle("Calculator");

        setComponents();
        setPanels();
        setEvents();

        setLayout(new GridLayout(2, 1));

        add(textPanel);
        add(buttonPanel);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void setComponents() {
        //계산기의 숫자 크기를 조절해주기 위해 Font를 적용
        textAreaNumbers.setText("0");
        textAreaNumbers.setFont(new Font("돋움", Font.BOLD, 44));
        //오른쪽에서 왼쪽방향으로 쓰여지게 설정
        textAreaNumbers.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        textAreaPreNumbers.setFont(new Font("돋움", Font.PLAIN, 22));
        textAreaPreNumbers.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        buttonNum0.setText("0");
        buttonNum1.setText("1");
        buttonNum2.setText("2");
        buttonNum3.setText("3");
        buttonNum4.setText("4");
        buttonNum5.setText("5");
        buttonNum6.setText("6");
        buttonNum7.setText("7");
        buttonNum8.setText("8");
        buttonNum9.setText("9");

        buttonMultiple.setText("X");
        buttonDevide.setText("÷");
        buttonMinus.setText("-");
        buttonPlus.setText("+");
        buttonRemainder.setText("%");
        buttonEqual.setText("=");
        buttonDot.setText(".");

        buttonClearAll.setText("AC");
        buttonDelete.setText("C");
        buttonReset.setText("Reset");

        buttonSave1.setText("S1");
        buttonSave2.setText("S2");
        buttonRecall1.setText("R1");
        buttonRecall2.setText("R2");
        buttonClear1.setText("C1");
        buttonClear2.setText("C2");

    }

    private void setPanels() {
        // Text Panel
        textPanel.setLayout(new GridLayout(2, 1));
        textPanel.add(textAreaPreNumbers);
        textPanel.add(textAreaNumbers);


        // Button Panel
        buttonPanel.setLayout(new GridLayout(7, 4));

        //맨 위에서부터 순서를 유지하며 넣어줘야 한다.
        buttonPanel.add(buttonClear1);
        buttonPanel.add(buttonClear2);
        buttonPanel.add(buttonReset);
        buttonPanel.add(buttonBlank1);

        buttonPanel.add(buttonSave1);
        buttonPanel.add(buttonSave2);
        buttonPanel.add(buttonRecall1);
        buttonPanel.add(buttonRecall2);


        buttonPanel.add(buttonDelete);
        buttonPanel.add(buttonClearAll);
        buttonPanel.add(buttonRemainder);
        buttonPanel.add(buttonDevide);

        buttonPanel.add(buttonNum7);
        buttonPanel.add(buttonNum8);
        buttonPanel.add(buttonNum9);
        buttonPanel.add(buttonMultiple);

        buttonPanel.add(buttonNum4);
        buttonPanel.add(buttonNum5);
        buttonPanel.add(buttonNum6);
        buttonPanel.add(buttonMinus);

        buttonPanel.add(buttonNum1);
        buttonPanel.add(buttonNum2);
        buttonPanel.add(buttonNum3);
        buttonPanel.add(buttonPlus);

        buttonPanel.add(buttonBlank2);
        buttonPanel.add(buttonNum0);
        buttonPanel.add(buttonDot);
        buttonPanel.add(buttonEqual);

    }



    private void calculate(String operator) {
        if (operator.equalsIgnoreCase("+")) {
            firstNum = Double.parseDouble(textAreaNumbers.getText());
            this.operator = "+";
            this.preOperator=operator;

            textAreaPreNumbers.setText(" + " + firstNum);
            textAreaNumbers.setText("");

        } else if (operator.equalsIgnoreCase("-")) {
            firstNum = Double.parseDouble(textAreaNumbers.getText());
            this.operator = "-";
            this.preOperator=operator;

            textAreaPreNumbers.setText(" - " + firstNum);
            textAreaNumbers.setText("");

        } else if (operator.equalsIgnoreCase("x")) {
            firstNum = Double.parseDouble(textAreaNumbers.getText());
            this.operator = "*";
            this.preOperator=operator;

            textAreaPreNumbers.setText(" X " + firstNum);
            textAreaNumbers.setText("");

        } else if (operator.equalsIgnoreCase("/")) {
            firstNum = Double.parseDouble(textAreaNumbers.getText());
            this.operator = "/";
            this.preOperator=operator;

            textAreaPreNumbers.setText(" / " + firstNum);
            textAreaNumbers.setText("");

        } else if (operator.equalsIgnoreCase("%")) {
            firstNum = Double.parseDouble(textAreaNumbers.getText());
            this.operator = "%";
            this.preOperator=operator;

            textAreaPreNumbers.setText(" % " + firstNum);
            textAreaNumbers.setText("");

        } else if (operator.equalsIgnoreCase("=")) {
            if (firstNum == 0) {
                return;
            }
            double result = 0;
            secondNum = Double.parseDouble(textAreaNumbers.getText());

            //연산의 결과를 보여주기 위한 코드
            textAreaPreNumbers.setText(firstNum+preOperator+secondNum);

            if (this.operator.equalsIgnoreCase("+")) {
                result = firstNum + secondNum;
            } else if (this.operator.equalsIgnoreCase("-")) {
                result = firstNum - secondNum;
            } else if (this.operator.equalsIgnoreCase("*")) {
                result = firstNum * secondNum;
            } else if (this.operator.equalsIgnoreCase("/")) {
                result = firstNum / secondNum;
            } else if (this.operator.equalsIgnoreCase("%")) {
                result = firstNum % secondNum;
            }

            firstNum = result;
            textAreaNumbers.setText(Double.toString(result));

        }
    }


    //계산기에 숫자를 반영할 때 발생하는 이벤트에 필요한 메소드
    private void updateNumber(int num) {
        textAreaNumbers.append(Integer.toString(num));
    }


    //addActionListener를 간결하게 표현하기 위해 람다식 사용
    private void setEvents() {
        buttonNum0.addActionListener(e -> {
            //계산기를 켰을 때 초기에 아무 입력값이 없으면 숫자 0이 화면에 나타나있으므로 이를 없애주고
            //원하는 숫자를 반영해준다.
            if (textAreaNumbers.getText().equalsIgnoreCase("0")) {
                textAreaNumbers.setText("");
            }
            updateNumber(0);
        });
        buttonNum1.addActionListener(e -> {
            if (textAreaNumbers.getText().equalsIgnoreCase("0")) {
                textAreaNumbers.setText("");
            }
            updateNumber(1);
        });
        buttonNum2.addActionListener(e -> {
            if (textAreaNumbers.getText().equalsIgnoreCase("0")) {
                textAreaNumbers.setText("");
            }
            updateNumber(2);
        });
        buttonNum3.addActionListener(e -> {
            if (textAreaNumbers.getText().equalsIgnoreCase("0")) {
                textAreaNumbers.setText("");
            }
            updateNumber(3);
        });
        buttonNum4.addActionListener(e -> {
            if (textAreaNumbers.getText().equalsIgnoreCase("0")) {
                textAreaNumbers.setText("");
            }
            updateNumber(4);
        });
        buttonNum5.addActionListener(e -> {
            if (textAreaNumbers.getText().equalsIgnoreCase("0")) {
                textAreaNumbers.setText("");
            }
            updateNumber(5);
        });
        buttonNum6.addActionListener(e -> {
            if (textAreaNumbers.getText().equalsIgnoreCase("0")) {
                textAreaNumbers.setText("");
            }
            updateNumber(6);
        });
        buttonNum7.addActionListener(e -> {
            if (textAreaNumbers.getText().equalsIgnoreCase("0")) {
                textAreaNumbers.setText("");
            }
            updateNumber(7);
        });
        buttonNum8.addActionListener(e -> {
            if (textAreaNumbers.getText().equalsIgnoreCase("0")) {
                textAreaNumbers.setText("");
            }
            updateNumber(8);
        });
        buttonNum9.addActionListener(e -> {
            if (textAreaNumbers.getText().equalsIgnoreCase("0")) {
                textAreaNumbers.setText("");
            }
            updateNumber(9);
        });

        buttonDot.addActionListener(e -> {
            textAreaNumbers.append(".");
        });

        //text상에있는 변수들을 초기화
        buttonClearAll.addActionListener(e -> {
            textAreaNumbers.setText("");
            textAreaPreNumbers.setText("");
            firstNum = 0;
            secondNum = 0;
            operator = "";
        });

        //계산기에 저장된 모든 데이터들을 초기화
        buttonReset.addActionListener(e -> {
            textAreaNumbers.setText("");
            textAreaPreNumbers.setText("");
            firstNum = 0;
            secondNum = 0;
            savedNum1 = 0;
            savedNum2 = 0;
            operator = "";
        });


        //저장하고 싶은 숫자 S1에 저장
        buttonSave1.addActionListener(e ->{
            savedNum1 = firstNum;
        });

        //저장하고 싶은 숫자 S2에 저장
        buttonSave2.addActionListener(e ->{
            savedNum2 = firstNum;
        });

        //S1에 저장된 숫자 Recall
        buttonRecall1.addActionListener(e -> {
            textAreaNumbers.setText(Double.toString(savedNum1));
        });

        //S2에 저장된 숫자 Recall
        buttonRecall2.addActionListener(e -> {
            textAreaNumbers.setText(Double.toString(savedNum2));
        });

        //S1에 저장된 숫자 Clear
        buttonClear1.addActionListener(e -> {
            savedNum1=0;
        });

        //S2에 저장된 숫자 Clear
        buttonClear2.addActionListener(e -> {
            savedNum2=0;
        });

        buttonDevide.addActionListener(e -> {
            calculate("/");
        });

        buttonPlus.addActionListener(e -> {
            calculate("+");
        });

        buttonMinus.addActionListener(e -> {
            calculate("-");
        });

        buttonMultiple.addActionListener(e -> {
            calculate("X");
        });

        buttonRemainder.addActionListener(e -> {
            calculate("%");
        });

        buttonEqual.addActionListener(e -> {
            calculate("=");
        });


        buttonDelete.addActionListener(e -> {
            String getNumber = textAreaNumbers.getText();
            if (getNumber.length() <= 0) {
                return;
            }
            String t = getNumber.substring(0, getNumber.length() - 1);
            textAreaNumbers.setText(t);
        });
    }

    public static void main(String[] args) {
        new Calculator();
    }

}
