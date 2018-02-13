/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcsetgui;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pcsetdemo.PCSet;

/**
 *
 * @author scottmitchell
 */
public class PCSetGui extends Application
{

    ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    ArrayList<Label> foundLabels = new ArrayList<>();
    ArrayList<TextArea> foundTextAreas = new ArrayList<>();
    ChoiceBox normalFormTranspose = new ChoiceBox();
    ChoiceBox t0ITranspose = new ChoiceBox();
    ChoiceBox primeFormTranspose = new ChoiceBox();
    ArrayList<TextArea> transposeTextAreas = new ArrayList<>();
    Button transpose, submit;
    ArrayList<Integer> inSet;
    PCSet pcset;

    @Override
    public void start(Stage primaryStage)
    {

        GridPane root = new GridPane();

        //checkboxes to choose pc
        CheckBox pc0 = new CheckBox("0");
        CheckBox pc1 = new CheckBox("1");
        CheckBox pc2 = new CheckBox("2");
        CheckBox pc3 = new CheckBox("3");
        CheckBox pc4 = new CheckBox("4");
        CheckBox pc5 = new CheckBox("5");
        CheckBox pc6 = new CheckBox("6");
        CheckBox pc7 = new CheckBox("7");
        CheckBox pc8 = new CheckBox("8");
        CheckBox pc9 = new CheckBox("9");
        CheckBox pct = new CheckBox("t");
        CheckBox pce = new CheckBox("e");

        //add them to arraylist
        checkBoxes.add(pc0);
        checkBoxes.add(pc1);
        checkBoxes.add(pc2);
        checkBoxes.add(pc3);
        checkBoxes.add(pc4);
        checkBoxes.add(pc5);
        checkBoxes.add(pc6);
        checkBoxes.add(pc7);
        checkBoxes.add(pc8);
        checkBoxes.add(pc9);
        checkBoxes.add(pct);
        checkBoxes.add(pce);

        for (int i = 0; i < checkBoxes.size(); i++)
        {
            root.add(checkBoxes.get(i), 0, i);
        }

        //labels for stuff found
        Label normalForm = new Label("Normal Form: ");
        Label t0I = new Label("T[0]I: ");
        Label primeForm = new Label("Prime Form: ");
        Label cardinality = new Label("Cardinality: ");
        Label intervalVector = new Label("IC Vector: ");

        //add to labels
        foundLabels.add(normalForm);
        foundLabels.add(t0I);
        foundLabels.add(primeForm);
        foundLabels.add(cardinality);
        foundLabels.add(intervalVector);

        for (int i = 0; i < foundLabels.size(); i++)
        {
            root.add(foundLabels.get(i), 2, i + 1);
        }

        //textAreas hold the answers for above labels
        TextArea normalFormAns = new TextArea();
        TextArea t0IAns = new TextArea();
        TextArea primeFormAns = new TextArea();
        TextArea cardinalityAns = new TextArea();
        TextArea intervalVectorAns = new TextArea();

        //add to textareas
        foundTextAreas.add(normalFormAns);
        foundTextAreas.add(t0IAns);
        foundTextAreas.add(primeFormAns);
        foundTextAreas.add(cardinalityAns);
        foundTextAreas.add(intervalVectorAns);

        foundTextAreas.forEach(a -> a.setEditable(false));
        foundTextAreas.forEach(a -> a.setPrefHeight(12));

        for (int i = 0; i < foundTextAreas.size(); i++)
        {
            root.add(foundTextAreas.get(i), 3, i + 1);
        }

        //transposition area
        //fill normal form choicebox
        normalFormTranspose.getItems().add("T[0]");
        normalFormTranspose.getItems().add("T[1]");
        normalFormTranspose.getItems().add("T[2]");
        normalFormTranspose.getItems().add("T[3]");
        normalFormTranspose.getItems().add("T[4]");
        normalFormTranspose.getItems().add("T[5]");
        normalFormTranspose.getItems().add("T[6]");
        normalFormTranspose.getItems().add("T[7]");
        normalFormTranspose.getItems().add("T[8]");
        normalFormTranspose.getItems().add("T[9]");
        normalFormTranspose.getItems().add("T[t]");
        normalFormTranspose.getItems().add("T[e]");
        normalFormTranspose.setValue("T[0]");

        // fill t0i choicebox
        t0ITranspose.getItems().add("T[0]I");
        t0ITranspose.getItems().add("T[1]I");
        t0ITranspose.getItems().add("T[2]I");
        t0ITranspose.getItems().add("T[3]I");
        t0ITranspose.getItems().add("T[4]I");
        t0ITranspose.getItems().add("T[5]I");
        t0ITranspose.getItems().add("T[6]I");
        t0ITranspose.getItems().add("T[7]I");
        t0ITranspose.getItems().add("T[8]I");
        t0ITranspose.getItems().add("T[9]I");
        t0ITranspose.getItems().add("T[t]I");
        t0ITranspose.getItems().add("T[e]I");
        t0ITranspose.setValue("T[0]I");

        //fill prime form choicebox
        primeFormTranspose.getItems().add("[0]");
        primeFormTranspose.getItems().add("[1]");
        primeFormTranspose.getItems().add("[2]");
        primeFormTranspose.getItems().add("[3]");
        primeFormTranspose.getItems().add("[4]");
        primeFormTranspose.getItems().add("[5]");
        primeFormTranspose.getItems().add("[6]");
        primeFormTranspose.getItems().add("[7]");
        primeFormTranspose.getItems().add("[8]");
        primeFormTranspose.getItems().add("[9]");
        primeFormTranspose.getItems().add("[t]");
        primeFormTranspose.getItems().add("[e]");
        primeFormTranspose.setValue("[0]");

        //add choice boxes
        root.add(normalFormTranspose, 2, 7);
        root.add(t0ITranspose, 2, 8);
        root.add(primeFormTranspose, 2, 9);

        //add choice boxes corresponding text areas
        transposeTextAreas.add(new TextArea());
        transposeTextAreas.add(new TextArea());
        transposeTextAreas.add(new TextArea());
        for (int i = 0; i < transposeTextAreas.size(); i++)
        {
            root.add(transposeTextAreas.get(i), 3, i + 7);
        }

        //add spacing pane
        ArrayList<Pane> panes = new ArrayList<>();

        for (int i = 0; i < 20; i++)
        {
            Pane spring = new Pane();
            panes.add(spring);
            spring.minHeightProperty().bind(foundTextAreas.get(0).heightProperty());
            spring.minWidthProperty().bind(checkBoxes.get(0).widthProperty());
            root.add(spring, 1, i);
        }

        //buttons
        submit = new Button("Submit");
        submit.setOnAction(new SubmitEventHandler());

        transpose = new Button("Transpose");
        transpose.setOnAction(new TranspositionEventHandler());

        //add buttons
        root.add(submit, 3, 10);
        root.add(transpose, 2, 10);

        root.setPadding(new Insets(12, 12, 12, 12));
        root.setPrefHeight(550);
        root.setPrefWidth(800);
        Scene scene = new Scene(root);

        primaryStage.setTitle("PCSet Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    class TranspositionEventHandler implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event)
        {
            switch (normalFormTranspose.getValue().toString())
            {
                case "T[0]":
                    transposeTextAreas.get(0).setText(pcset.getNormalForm().toString());
                    break;
                case "T[1]":
                    transposeTextAreas.get(0).setText(pcset.transpose(pcset.getNormalForm(), 1).toString());
                    break;
                case "T[2]":
                    transposeTextAreas.get(0).setText(pcset.transpose(pcset.getNormalForm(), 2).toString());
                    break;
                case "T[3]":
                    transposeTextAreas.get(0).setText(pcset.transpose(pcset.getNormalForm(), 3).toString());
                    break;
                case "T[4]":
                    transposeTextAreas.get(0).setText(pcset.transpose(pcset.getNormalForm(), 4).toString());
                    break;
                case "T[5]":
                    transposeTextAreas.get(0).setText(pcset.transpose(pcset.getNormalForm(), 5).toString());
                    break;
                case "T[6]":
                    transposeTextAreas.get(0).setText(pcset.transpose(pcset.getNormalForm(), 6).toString());
                    break;
                case "T[7]":
                    transposeTextAreas.get(0).setText(pcset.transpose(pcset.getNormalForm(), 7).toString());
                    break;
                case "T[8]":
                    transposeTextAreas.get(0).setText(pcset.transpose(pcset.getNormalForm(), 8).toString());
                    break;
                case "T[9]":
                    transposeTextAreas.get(0).setText(pcset.transpose(pcset.getNormalForm(), 9).toString());
                    break;
                case "T[t]":
                    transposeTextAreas.get(0).setText(pcset.transpose(pcset.getNormalForm(), 10).toString());
                    break;
                case "T[e]":
                    transposeTextAreas.get(0).setText(pcset.transpose(pcset.getNormalForm(), 11).toString());
                    break;
                default:
                    break;
            }
            
            switch (t0ITranspose.getValue().toString())
            {
                case "T[0]I":
                    transposeTextAreas.get(1).setText(pcset.getT0Inversion().toString());
                    break;
                case "T[1]I":
                    transposeTextAreas.get(1).setText(pcset.transpose(pcset.getT0Inversion(), 1).toString());
                    break;
                case "T[2]I":
                    transposeTextAreas.get(1).setText(pcset.transpose(pcset.getT0Inversion(), 2).toString());
                    break;
                case "T[3]I":
                    transposeTextAreas.get(1).setText(pcset.transpose(pcset.getT0Inversion(), 3).toString());
                    break;
                case "T[4]I":
                    transposeTextAreas.get(1).setText(pcset.transpose(pcset.getT0Inversion(), 4).toString());
                    break;
                case "T[5]I":
                    transposeTextAreas.get(1).setText(pcset.transpose(pcset.getT0Inversion(), 5).toString());
                    break;
                case "T[6]I":
                    transposeTextAreas.get(1).setText(pcset.transpose(pcset.getT0Inversion(), 6).toString());
                    break;
                case "T[7]I":
                    transposeTextAreas.get(1).setText(pcset.transpose(pcset.getT0Inversion(), 7).toString());
                    break;
                case "T[8]I":
                    transposeTextAreas.get(1).setText(pcset.transpose(pcset.getT0Inversion(), 8).toString());
                    break;
                case "T[9]I":
                    transposeTextAreas.get(1).setText(pcset.transpose(pcset.getT0Inversion(), 9).toString());
                    break;
                case "T[t]I":
                    transposeTextAreas.get(1).setText(pcset.transpose(pcset.getT0Inversion(), 10).toString());
                    break;
                case "T[e]I":
                    transposeTextAreas.get(1).setText(pcset.transpose(pcset.getT0Inversion(), 11).toString());
                    break;
                default:
                    break;
            }
            
            switch (primeFormTranspose.getValue().toString())
            {
                case "[0]":
                    transposeTextAreas.get(2).setText(pcset.getPrimeForm().toString());
                    break;
                case "[1]":
                    transposeTextAreas.get(2).setText(pcset.transpose(pcset.getPrimeForm(), 1).toString());
                    break;
                case "[2]":
                    transposeTextAreas.get(2).setText(pcset.transpose(pcset.getPrimeForm(), 2).toString());
                    break;
                case "[3]":
                    transposeTextAreas.get(2).setText(pcset.transpose(pcset.getPrimeForm(), 3).toString());
                    break;
                case "[4]":
                    transposeTextAreas.get(2).setText(pcset.transpose(pcset.getPrimeForm(), 4).toString());
                    break;
                case "[5]":
                    transposeTextAreas.get(2).setText(pcset.transpose(pcset.getPrimeForm(), 5).toString());
                    break;
                case "[6]":
                    transposeTextAreas.get(2).setText(pcset.transpose(pcset.getPrimeForm(), 6).toString());
                    break;
                case "[7]":
                    transposeTextAreas.get(2).setText(pcset.transpose(pcset.getPrimeForm(), 7).toString());
                    break;
                case "[8]":
                    transposeTextAreas.get(2).setText(pcset.transpose(pcset.getPrimeForm(), 8).toString());
                    break;
                case "[9]":
                    transposeTextAreas.get(2).setText(pcset.transpose(pcset.getPrimeForm(), 9).toString());
                    break;
                case "[t]":
                    transposeTextAreas.get(2).setText(pcset.transpose(pcset.getPrimeForm(), 10).toString());
                    break;
                case "[e]":
                    transposeTextAreas.get(2).setText(pcset.transpose(pcset.getPrimeForm(), 11).toString());
                    break;
                default:
                    break;
            }
        }
    }

    class SubmitEventHandler implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event)
        {
            inSet = new ArrayList<>();
            for (CheckBox chk : checkBoxes)
            {
                if (chk.isSelected())
                {
                    if (chk.getText().equals("t"))
                    {
                        inSet.add(10);
                    } else if (chk.getText().equals("e"))
                    {
                        inSet.add(11);
                    } else
                    {
                        int in = Integer.valueOf(chk.getText());
                        inSet.add(in);
                    }
                }

            }

            pcset = new PCSet(inSet);
            foundTextAreas.get(0).setText(pcset.getNormalForm().toString());
            foundTextAreas.get(1).setText(pcset.getT0Inversion().toString());
            foundTextAreas.get(2).setText(pcset.getPrimeForm().toString());
            foundTextAreas.get(3).setText(pcset.getCardinality());
            foundTextAreas.get(4).setText(pcset.getIntervalVector());
            
            for (TextArea area : transposeTextAreas)
            {
                area.setText("");
            }
        }
    }

}
