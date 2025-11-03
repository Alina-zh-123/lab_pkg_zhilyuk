import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
    
    private Color currentColor = Color.WHITE;
    private boolean updating = false;
    private boolean initializing = true;
    
    private Slider sliderR, sliderG, sliderB;
    private TextField textR, textG, textB;
    
    private Slider sliderX, sliderY_xyz, sliderZ;
    private TextField textX, textY_xyz, textZ;
    
    private Slider sliderC, sliderM, sliderY_cmyk, sliderK;
    private TextField textC, textM, textY_cmyk, textK;
    
    private Label warningLabel;
    private Rectangle colorPreview;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Конструктор цвета - RGB, XYZ, CMYK");
        
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setStyle("-fx-background-color: #232b43;");
        
        colorPreview = new Rectangle(830, 80);
        colorPreview.setArcWidth(20);
        colorPreview.setArcHeight(20);
        colorPreview.setFill(currentColor);
        
        warningLabel = new Label();
        warningLabel.setStyle("-fx-text-fill: #ff6b6b; -fx-font-weight: bold;");
        
        VBox rgbPanel = createRGBPanel();
        VBox xyzPanel = createXYZPanel();
        VBox cmykPanel = createCMYKPanel();
        
        ColorPicker colorPicker = new ColorPicker(currentColor);
        colorPicker.setOnAction(e -> {
            if (!updating) {
                clearWarning();
                setColor(colorPicker.getValue());
            }
        });
        
        HBox colorModelsPanel = new HBox(20);
        colorModelsPanel.getChildren().addAll(rgbPanel, xyzPanel, cmykPanel);
        
        HBox topPanel = new HBox(20, colorPreview, colorPicker);
        
        mainContainer.getChildren().addAll(
            topPanel, warningLabel, colorModelsPanel
        );
        
        Scene scene = new Scene(mainContainer, 1100, 440);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        updateAllDisplays();
        
        initializing = false;
        clearWarning();
    }
    
    private void clearWarning() {
        warningLabel.setText("");
    }
    
    private VBox createRGBPanel() {
        Label title = new Label("RGB Color Model");
        title.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
        
        sliderR = createColorSlider(0, 255, 255);
        sliderG = createColorSlider(0, 255, 255);
        sliderB = createColorSlider(0, 255, 255);
        
        textR = createColorTextField("255");
        textG = createColorTextField("255");
        textB = createColorTextField("255");
        
        bindRGBControls();
        
        GridPane rgbGrid = new GridPane();
        rgbGrid.setHgap(10);
        rgbGrid.setVgap(8);
        rgbGrid.add(new Label("R:"), 0, 0);
        rgbGrid.add(sliderR, 1, 0);
        rgbGrid.add(textR, 2, 0);
        rgbGrid.add(new Label("G:"), 0, 1);
        rgbGrid.add(sliderG, 1, 1);
        rgbGrid.add(textG, 2, 1);
        rgbGrid.add(new Label("B:"), 0, 2);
        rgbGrid.add(sliderB, 1, 2);
        rgbGrid.add(textB, 2, 2);
        
        VBox panel = new VBox(15, title, rgbGrid);
        panel.setStyle("-fx-background-color: #3b4359; -fx-padding: 15; -fx-background-radius: 10;");
        panel.setPrefWidth(350);
        return panel;
    }
    
    private VBox createXYZPanel() {
        Label title = new Label("XYZ Color Model");
        title.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        sliderX = createColorSlider(0, 95.0456, 95.0456);
        sliderY_xyz = createColorSlider(0, 100.000, 100.000);
        sliderZ = createColorSlider(0, 108.8754, 108.8754);
        
        textX = createColorTextField("95.0456");
        textY_xyz = createColorTextField("100.00");
        textZ = createColorTextField("108.8754");
        
        bindXYZControls();
        
        GridPane xyzGrid = new GridPane();
        xyzGrid.setHgap(10);
        xyzGrid.setVgap(8);
        xyzGrid.add(new Label("X:"), 0, 0);
        xyzGrid.add(sliderX, 1, 0);
        xyzGrid.add(textX, 2, 0);
        xyzGrid.add(new Label("Y:"), 0, 1);
        xyzGrid.add(sliderY_xyz, 1, 1);
        xyzGrid.add(textY_xyz, 2, 1);
        xyzGrid.add(new Label("Z:"), 0, 2);
        xyzGrid.add(sliderZ, 1, 2);
        xyzGrid.add(textZ, 2, 2);
        
        VBox panel = new VBox(15, title, xyzGrid);
        panel.setStyle("-fx-background-color: #3b4359; -fx-padding: 15; -fx-background-radius: 10;");
        panel.setPrefWidth(350);
        return panel;
    }
    
    private VBox createCMYKPanel() {
        Label title = new Label("CMYK Color Model");
        title.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
        
        sliderC = createColorSlider(0, 100, 0);
        sliderM = createColorSlider(0, 100, 0);
        sliderY_cmyk = createColorSlider(0, 100, 0);
        sliderK = createColorSlider(0, 100, 0);
        
        textC = createColorTextField("0");
        textM = createColorTextField("0");
        textY_cmyk = createColorTextField("0");
        textK = createColorTextField("0");
        
        bindCMYKControls();
        
        GridPane cmykGrid = new GridPane();
        cmykGrid.setHgap(10);
        cmykGrid.setVgap(8);
        cmykGrid.add(new Label("C:"), 0, 0);
        cmykGrid.add(sliderC, 1, 0);
        cmykGrid.add(textC, 2, 0);
        cmykGrid.add(new Label("M:"), 0, 1);
        cmykGrid.add(sliderM, 1, 1);
        cmykGrid.add(textM, 2, 1);
        cmykGrid.add(new Label("Y:"), 0, 2);
        cmykGrid.add(sliderY_cmyk, 1, 2);
        cmykGrid.add(textY_cmyk, 2, 2);
        cmykGrid.add(new Label("K:"), 0, 3);
        cmykGrid.add(sliderK, 1, 3);
        cmykGrid.add(textK, 2, 3);
        
        VBox panel = new VBox(15, title, cmykGrid);
        panel.setStyle("-fx-background-color: #3b4359; -fx-padding: 15; -fx-background-radius: 10;");
        panel.setPrefWidth(350);
        return panel;
    }
    
    private Slider createColorSlider(double min, double max, double value) {
        Slider slider = new Slider(min, max, value);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit((max - min) / 4);
        slider.setBlockIncrement((max - min) / 100);
        slider.setPrefWidth(200);
        return slider;
    }
    
    private TextField createColorTextField(String initialValue) {
        TextField field = new TextField(initialValue);
        field.setPrefWidth(60);
        return field;
    }
    
    private void bindRGBControls() {
        ChangeListener<Number> rgbSliderListener = (obs, oldVal, newVal) -> {
            if (!updating && !initializing) {
                clearWarning();
                int r = (int) Math.round(sliderR.getValue());
                int g = (int) Math.round(sliderG.getValue());
                int b = (int) Math.round(sliderB.getValue());
                setColor(Color.rgb(r, g, b));
            }
        };
        
        sliderR.valueProperty().addListener(rgbSliderListener);
        sliderG.valueProperty().addListener(rgbSliderListener);
        sliderB.valueProperty().addListener(rgbSliderListener);
        
        textR.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!updating && !initializing && !newVal.equals(oldVal)) {
                clearWarning();
                javafx.application.Platform.runLater(() -> updateFromRGBText());
            }
        });
        textG.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!updating && !initializing && !newVal.equals(oldVal)) {
                clearWarning();
                javafx.application.Platform.runLater(() -> updateFromRGBText());
            }
        });
        textB.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!updating && !initializing && !newVal.equals(oldVal)) {
                clearWarning();
                javafx.application.Platform.runLater(() -> updateFromRGBText());
            }
        });
    }
    
    private void bindXYZControls() {
        ChangeListener<Number> xyzSliderListener = (obs, oldVal, newVal) -> {
            if (!updating && !initializing) {
                clearWarning();
                double x = sliderX.getValue();
                double y = sliderY_xyz.getValue();
                double z = sliderZ.getValue();
                Color xyzColor = xyzToRgb(x, y, z);
                setColor(xyzColor);
            }
        };
        
        sliderX.valueProperty().addListener(xyzSliderListener);
        sliderY_xyz.valueProperty().addListener(xyzSliderListener);
        sliderZ.valueProperty().addListener(xyzSliderListener);
        
        textX.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!updating && !initializing && !newVal.equals(oldVal)) {
                clearWarning();
                javafx.application.Platform.runLater(() -> updateFromXYZText());
            }
        });

        textX.setOnAction(e -> {
            if (!updating && !initializing) {
                clearWarning();
                updateFromXYZText();
            }
        });

        textX.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && !updating && !initializing) {
                clearWarning();
                updateFromXYZText();
            }
        });
        textY_xyz.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!updating && !initializing && !newVal.equals(oldVal)) {
                clearWarning();
                javafx.application.Platform.runLater(() -> updateFromXYZText());
            }
        });
        textZ.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!updating && !initializing && !newVal.equals(oldVal)) {
                clearWarning();
                javafx.application.Platform.runLater(() -> updateFromXYZText());
            }
        });
    }
    
    private void bindCMYKControls() {
        ChangeListener<Number> cmykSliderListener = (obs, oldVal, newVal) -> {
            if (!updating && !initializing) {
                clearWarning();
                double c = sliderC.getValue() / 100.0;
                double m = sliderM.getValue() / 100.0;
                double y_val = sliderY_cmyk.getValue() / 100.0;
                double k = sliderK.getValue() / 100.0;
                Color cmykColor = cmykToRgb(c, m, y_val, k);
                setColor(cmykColor);
            }
        };
        
        sliderC.valueProperty().addListener(cmykSliderListener);
        sliderM.valueProperty().addListener(cmykSliderListener);
        sliderY_cmyk.valueProperty().addListener(cmykSliderListener);
        sliderK.valueProperty().addListener(cmykSliderListener);
        
        textC.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!updating && !initializing && !newVal.equals(oldVal)) {
                clearWarning();
                javafx.application.Platform.runLater(() -> updateFromCMYKText());
            }
        });
        textM.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!updating && !initializing && !newVal.equals(oldVal)) {
                clearWarning();
                javafx.application.Platform.runLater(() -> updateFromCMYKText());
            }
        });
        textY_cmyk.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!updating && !initializing && !newVal.equals(oldVal)) {
                clearWarning();
                javafx.application.Platform.runLater(() -> updateFromCMYKText());
            }
        });
        textK.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!updating && !initializing && !newVal.equals(oldVal)) {
                clearWarning();
                javafx.application.Platform.runLater(() -> updateFromCMYKText());
            }
        });
    }
    
    private void updateFromRGBText() {
        if (!updating && !initializing) {
            try {
                int r = Integer.parseInt(textR.getText());
                int g = Integer.parseInt(textG.getText());
                int b = Integer.parseInt(textB.getText());
                
                if (r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255) {
                    setColor(Color.rgb(r, g, b));
                    clearWarning();
                } else {
                    warningLabel.setText("RGB значения должны быть в диапазоне 0-255");
                }
            } catch (NumberFormatException e) {
                warningLabel.setText("Некорректный формат RGB значений");
            }
        }
    }
    
    private void updateFromXYZText() {
        if (!updating && !initializing) {
            try {
                double x = Double.parseDouble(textX.getText().trim().replace(",", "."));
                double y = Double.parseDouble(textY_xyz.getText().trim().replace(",", "."));
                double z = Double.parseDouble(textZ.getText().trim().replace(",", "."));

                if (x >= 0 && x <= 95.0456 && y >= 0 && y <= 100.000 && z >= 0 && z <= 108.8754) {
                    Color xyzColor = xyzToRgb(x, y, z);
                    setColor(xyzColor);
                } else {
                    warningLabel.setText("X: 0–95.0456, Y: 0–100, Z: 0–108.8754");
                }

            } catch (NumberFormatException e) {
                warningLabel.setText("Некорректный формат XYZ значений");
            }
        }
    }
    
    private void updateFromCMYKText() {
        if (!updating && !initializing) {
            try {
                double c = Double.parseDouble(textC.getText().trim().replace(",", "."));
                double m = Double.parseDouble(textM.getText().trim().replace(",", "."));
                double y_val = Double.parseDouble(textY_cmyk.getText().trim().replace(",", "."));
                double k = Double.parseDouble(textK.getText().trim().replace(",", "."));

                if (c >= 0 && c <= 100 && m >= 0 && m <= 100 && y_val >= 0 && y_val <= 100 && k >= 0 && k <= 100) {
                    Color cmykColor = cmykToRgb(c / 100.0, m / 100.0, y_val / 100.0, k / 100.0);
                    setColor(cmykColor);
                    clearWarning();
                } else {
                    warningLabel.setText("CMYK значения должны быть в диапазоне 0–100");
                }

            } catch (NumberFormatException e) {
                warningLabel.setText("Некорректный формат CMYK значений");
            }
        }
    }
    
    private void setColor(Color newColor) {
        updating = true;
        currentColor = newColor;
        updateAllDisplays();
        updating = false;
    }
    
    private void updateAllDisplays() {
        updateRGBDisplay();
        updateXYZDisplay();
        updateCMYKDisplay();
        colorPreview.setFill(currentColor);
    }
    
    private void updateRGBDisplay() {
        int r = (int) Math.round(currentColor.getRed() * 255);
        int g = (int) Math.round(currentColor.getGreen() * 255);
        int b = (int) Math.round(currentColor.getBlue() * 255);
        
        sliderR.setValue(r);
        sliderG.setValue(g);
        sliderB.setValue(b);
        
        textR.setText(String.valueOf(r));
        textG.setText(String.valueOf(g));
        textB.setText(String.valueOf(b));
    }
    
    private void updateXYZDisplay() {
        double[] xyz = rgbToXyz(currentColor);
        sliderX.setValue(xyz[0]);
        sliderY_xyz.setValue(xyz[1]);
        sliderZ.setValue(xyz[2]);
        
        textX.setText(String.format("%.2f", xyz[0]));
        textY_xyz.setText(String.format("%.2f", xyz[1]));
        textZ.setText(String.format("%.2f", xyz[2]));
    }
    
    private void updateCMYKDisplay() {
        double[] cmyk = rgbToCmyk(currentColor);
        sliderC.setValue(cmyk[0] * 100);
        sliderM.setValue(cmyk[1] * 100);
        sliderY_cmyk.setValue(cmyk[2] * 100);
        sliderK.setValue(cmyk[3] * 100);
        
        textC.setText(String.format("%.1f", cmyk[0] * 100));
        textM.setText(String.format("%.1f", cmyk[1] * 100));
        textY_cmyk.setText(String.format("%.1f", cmyk[2] * 100));
        textK.setText(String.format("%.1f", cmyk[3] * 100));
    }
    
    private double[] rgbToXyz(Color color) {
        double r = color.getRed();
        double g = color.getGreen();
        double b = color.getBlue();
        
        r = (r > 0.04045) ? Math.pow((r + 0.055) / 1.055, 2.4) : r / 12.92;
        g = (g > 0.04045) ? Math.pow((g + 0.055) / 1.055, 2.4) : g / 12.92;
        b = (b > 0.04045) ? Math.pow((b + 0.055) / 1.055, 2.4) : b / 12.92;
        
        double x = (r * 0.412453 + g * 0.357580 + b * 0.180423) * 100.0;
        double y = (r * 0.212671 + g * 0.715160 + b * 0.072169) * 100.0;
        double z = (r * 0.019334 + g * 0.119193 + b * 0.950227) * 100.0;
        
        return new double[]{x, y, z};
    }
    
    private Color xyzToRgb(double x, double y, double z) {
        x /= 100.0;
        y /= 100.0;
        z /= 100.0;
        
        double rLinear = x * 3.2406 - y * 1.5372 - z * 0.4986;
        double gLinear = -x * 0.9689 + y * 1.8758 + z * 0.0415;
        double bLinear = x * 0.0557 - y * 0.2040 + z * 1.0570;
        
        double r = (rLinear > 0.0031308) ? (1.055 * Math.pow(rLinear, 1.0 / 2.4) - 0.055) : 12.92 * rLinear;
        double g = (gLinear > 0.0031308) ? (1.055 * Math.pow(gLinear, 1.0 / 2.4) - 0.055) : 12.92 * gLinear;
        double b = (bLinear > 0.0031308) ? (1.055 * Math.pow(bLinear, 1.0 / 2.4) - 0.055) : 12.92 * bLinear;
        
        boolean clipped = false;
        if (r < 0 || r > 1 || g < 0 || g > 1 || b < 0 || b > 1) {
            clipped = true;

            r = clamp(r, 0, 1);
            g = clamp(g, 0, 1);
            b = clamp(b, 0, 1);
        }
        
        if (clipped && !initializing) {
            warningLabel.setText("Внимание: XYZ значения выходят за границы RGB. Произведено обрезание.");
        }
        
        return Color.color(r, g, b);
    }

    private double min(double a, double b, double c) {
        return Math.min(a, Math.min(b, c));
    }
    
    private double[] rgbToCmyk(Color color) {
        double r = color.getRed();
        double g = color.getGreen();
        double b = color.getBlue();
        
        double k = 1.0 - Math.max(r, Math.max(g, b));
        
        if (k >= 0.9999) {
            return new double[]{0.0, 0.0, 0.0, 1.0};
        }
    
        if (k <= 0.0001) {
            return new double[]{
                (1.0 - r - k) / (1.0 - k),
                (1.0 - g - k) / (1.0 - k), 
                (1.0 - b - k) / (1.0 - k),
                k
            };
        }
        
        double c = (1.0 - r - k) / (1.0 - k);
        double m = (1.0 - g - k) / (1.0 - k);
        double y = (1.0 - b - k) / (1.0 - k);
        
        c = Math.round(clamp(c, 0, 1) * 1000.0) / 1000.0;
        m = Math.round(clamp(m, 0, 1) * 1000.0) / 1000.0;
        y = Math.round(clamp(y, 0, 1) * 1000.0) / 1000.0;
        k = Math.round(clamp(k, 0, 1) * 1000.0) / 1000.0;
        
        return new double[]{c, m, y, k};
    }
    
    private Color cmykToRgb(double c, double m, double y, double k) {
        c = clamp(c, 0, 1);
        m = clamp(m, 0, 1);
        y = clamp(y, 0, 1);
        k = clamp(k, 0, 1);
        
        double r = (1.0 - c) * (1.0 - k);
        double g = (1.0 - m) * (1.0 - k);
        double b = (1.0 - y) * (1.0 - k);
        
        r = Math.round(r * 1000.0) / 1000.0;
        g = Math.round(g * 1000.0) / 1000.0;
        b = Math.round(b * 1000.0) / 1000.0;
        
        return Color.color(r, g, b);
    }
    
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}