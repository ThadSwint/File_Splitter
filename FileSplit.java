import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Thad Swint
 *
 */

public class FileSplit extends Application {
	// create labels and text fields
	Label lblFile = new Label("Specify a file with absolute path");
	TextField tfFile = new TextField();
	Label lblSplits = new Label("Specify the number of splits for file");
	TextField tfSplits = new TextField();

	@Override
	public void start(Stage primaryStage) {
		// creating a pane to place nodes on
		BorderPane fsPane = new BorderPane();
		Label directions = new Label(
				"When spliting files into smaller files the resulting split files will be labeled source name.1 .2 ect");

		// btn node
		Button btSplit = new Button("SPLIT FILE");
		// call my function upon action
		btSplit.setOnAction(e -> theSplitting());

		// grid pane to format some nodes and some general alignment
		GridPane gP = new GridPane();
		gP.setHgap(15);
		gP.setVgap(7);
		gP.add(directions, 0, 0, 2, 1);
		gP.addRow(2, lblFile, tfFile);
		gP.addRow(4, lblSplits, tfSplits);
		fsPane.setCenter(gP);
		fsPane.setBottom(btSplit);
		BorderPane.setAlignment(btSplit, Pos.BOTTOM_CENTER);

		// setting of the scene.
		Scene scene = new Scene(fsPane, 500, 150);
		primaryStage.setTitle("File Splitter");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	// this is where the magic happens
	private void theSplitting() {
		// create file of file entered
		File file = new File(tfFile.getText());
		// pass string of splits to int
		int splits = Integer.parseInt(tfSplits.getText());
		// new filesize
		long fileSize = file.length() / splits + 1;
		// b input
		try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
			for (int i = 1; i <= splits; i++) {
				int value;
				// output
				try (BufferedOutputStream output = new BufferedOutputStream(
						new FileOutputStream(new File(file.getAbsoluteFile() + "." + i)))) {
					int count = 0;
					while (count++ < fileSize && (value = input.read()) != -1) {
						output.write(value);
					}
					output.close();
				}

			}
			input.close();
		} catch (FileNotFoundException fnf) {
			JOptionPane.showConfirmDialog(null, "FileNotFound, try absolute path");
		} catch (IOException ioex) {
			JOptionPane.showConfirmDialog(null, "Wrong File");
		}

	}

	public static void main(String[] args) {
		Application.launch(args);

	}
}