package fx.gui;

import encode.HEXConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * @author Arthur Kupriyanov
 */

public class EncodeMenuController {


    @FXML
    private Button convertBtn;

    @FXML
    private TextField isoLabel;

    @FXML
    private TextField utf8Label;

    @FXML
    private TextField koi8Label;

    @FXML
    private TextField utf16Label;

    @FXML
    private TextField windows1251Label;

    @FXML
    private TextField asciiLabel;

    @FXML
    public TextField originalLabel;

    @FXML
    void convert(ActionEvent event) {
        String context = originalLabel.getText();

        if (context.isEmpty()) return;

        isoLabel.setText(HEXConverter.getISO8859_5HexCode(context));
        utf8Label.setText(HEXConverter.getUTF8HexCode(context));
        utf16Label.setText(HEXConverter.getUTF16HexCode(context));
        windows1251Label.setText(HEXConverter.getWindows1251HexCode(context));
        asciiLabel.setText(HEXConverter.getASCIIHexCode(context));
        koi8Label.setText(HEXConverter.getKOI8RHexCode(context));

    }

}