package translation;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JSONTranslator jsonTranslator = new JSONTranslator("sample.json");

            // COUNTRIES

            JPanel countryPanel = new JPanel();

            CountryCodeConverter countryCodeconverter = new CountryCodeConverter("country-codes.txt");
            List<String> allCountryCodesListJSON = jsonTranslator.getCountryCodes();

            List<String> countryList = new ArrayList<>();
            for (String countryCode : allCountryCodesListJSON) {
                String countryName = countryCodeconverter.fromCountryCode(countryCode);
                countryList.add(countryName);
            }

             String[] optionsArray1 = countryList.toArray(new String[0]);
            // JComboBox<String> dropdown = new JComboBox<>(optionsArray);
            // dropdown.setPreferredSize(new Dimension(300, 40));
            // countryPanel.add(dropdown);

            // Adding countries to a scrollable list
            JList countryScrollable = new JList(optionsArray1);
            JScrollPane scrollPane = new JScrollPane(countryScrollable);
            scrollPane.setPreferredSize(new Dimension(200, 150));
            countryPanel.add(scrollPane);

            // LANGUAGES

            JPanel languagePanel = new JPanel();
            LanguageCodeConverter languageCodeconverter = new LanguageCodeConverter("language-codes.txt");

            List<String> languageList = new ArrayList<>();
            for (String languageCode : jsonTranslator.getLanguageCodes()) {
                String languageName = languageCodeconverter.fromLanguageCode(languageCode);
                languageList.add(languageName);
            }

            // Adding languages to a scrollable dropdown
            String[] optionsArray2 = languageList.toArray(new String[0]);
            JComboBox<String> languageScrollable = new JComboBox<>(optionsArray2);
            languageScrollable.setPreferredSize(new Dimension(200, 40));

            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageScrollable);

            JPanel buttonPanel = new JPanel();

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the countries in the scrollable list
            countryScrollable.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String language = (String) languageScrollable.getSelectedItem();
                    String country = (String) countryScrollable.getSelectedValue();

                    String countryCode = countryCodeconverter.fromCountry(country);
                    String languageCode = languageCodeconverter.fromLanguage(language);

                    Translator translator = new JSONTranslator("sample.json");
                    String result = translator.translate(countryCode, languageCode);

                    // FOR DEBUGGING:
                    // System.out.println("Converted country code: " + countryCode);
                    // System.out.println("Converted language code: " + languageCode);
                    // System.out.println("num of countries " + countryList.size());
                    // System.out.println("num of languages " + languageList.size());

                    if (result == null) {
                        result = "no translation found!";
                    }
                        resultLabel.setText(result);

                    }


            });

            // adding listener for when the user clicks the languages in the scrollable dropdown
            languageScrollable.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = (String) languageScrollable.getSelectedItem();
                    String country = (String) countryScrollable.getSelectedValue();

                    String countryCode = countryCodeconverter.fromCountry(country);
                    String languageCode = languageCodeconverter.fromLanguage(language);

                    Translator translator = new JSONTranslator("sample.json");
                    String result = translator.translate(countryCode, languageCode);


                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }


            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        });
    }
