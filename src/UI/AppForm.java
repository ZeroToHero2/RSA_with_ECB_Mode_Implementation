package UI;

import RSA.Decryption;
import RSA.Encryption;
import RSA.KeyGenerator;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AppForm {
    private JFrame frame;
    private JButton createKeyPairButton;
    private JTextField enterTextToEncryptTextField;
    private JButton encryptButton;
    private JPanel encryptionPanel;
    private JPanel encryptionWrapper;
    private JPanel KeyGenAndDecryptionPanel;
    private JPanel MainWrapper;
    private JPanel MainPanel;
    private JTextArea EncryptedText;
    private JLabel EncryptionLabel;
    private JTextArea DecryptedText;
    private JLabel DecryptedTextLabel;
    private JLabel GreetingsLabel;
    private JLabel keyPairsLabel;
    private JButton decryptButton;
    private JList<String> keyList2;
    private JScrollPane keyListScrollPane;
    private ArrayList<KeyGenerator> keys = new ArrayList<>(); // Array for user generated keys
    public static int selected_index;
    public static int flag;
    private Encryption encryption;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                AppForm window = new AppForm();
                window.frame.setVisible(true);
            }
        });
    }

    /**
     * Create the application.
     */
    public AppForm() {
        init();
    }

    private void init() {

        /**
         * Init Main Frame of the application.
         */
        frame = new JFrame("RSA Algorithm with ECB Mode");
        frame.setBounds(100, 100, 1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainPanel = new JPanel();
        frame.setContentPane(MainPanel);
        MainPanel.setLayout(new GridBagLayout());
        MainWrapper = new JPanel();
        MainWrapper.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        MainPanel.add(MainWrapper, gbc);

        /**
         * KeyPair Panel of the application.
         */
        encryptionWrapper = new JPanel();
        encryptionWrapper.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        MainWrapper.add(encryptionWrapper, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        keyPairsLabel = new JLabel();
        keyPairsLabel.setText("Key Pairs:");
        keyPairsLabel.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        encryptionWrapper.add(keyPairsLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        keyListScrollPane = new JScrollPane();
        encryptionWrapper.add(keyListScrollPane, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        JList<String> keyList = new JList<String>();
        final DefaultListModel<String> defaultListModel1 = new DefaultListModel<String>();
        keyList2.setModel(defaultListModel1);
        keyListScrollPane.setViewportView(keyList2);
        createKeyPairButton = new JButton();
        createKeyPairButton.setHorizontalTextPosition(0);
        createKeyPairButton.setText("Create Key Pair");
        createKeyPairButton.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        encryptionWrapper.add(createKeyPairButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        /**
         * Encryption Panel of the application.
         */
        encryptionPanel = new JPanel();
        encryptionPanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        encryptionWrapper.add(encryptionPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        enterTextToEncryptTextField = new JTextField();
        enterTextToEncryptTextField.setText("");
        encryptionPanel.add(enterTextToEncryptTextField, new GridConstraints(0, 1, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, -1), null, 0, false));
        GreetingsLabel = new JLabel();
        GreetingsLabel.setText(" Welcome to CMPE 325 RSA Algorithm Extra-Project");
        GreetingsLabel.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        JLabel enterTextLabel = new JLabel();
        enterTextLabel.setText(" Enter Plaintext to encrypt it:");
        enterTextLabel.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        encryptionPanel.add(enterTextLabel, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.ANCHOR_CENTER, GridConstraints.ALIGN_LEFT, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        encryptionPanel.add(GreetingsLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        encryptButton = new JButton();
        encryptButton.setText("Encrypt");
        encryptButton.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        encryptionPanel.add(encryptButton, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JLabel decryptLabel = new JLabel();
        decryptLabel.setText(" Decrypt It Back: ");
        decryptLabel.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        decryptButton = new JButton();
        decryptButton.setText("Decrypt");
        decryptButton.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        encryptionPanel.add(decryptButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        encryptionPanel.add(decryptLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        /**
         * Key Generation Panel of the application.
         */
        KeyGenAndDecryptionPanel = new JPanel();
        KeyGenAndDecryptionPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        MainPanel.add(KeyGenAndDecryptionPanel, gbc);
        JScrollPane encryptionScrollPane = new JScrollPane();
        EncryptedText = new JTextArea();
        EncryptedText.setLineWrap(true);
        EncryptedText.setWrapStyleWord(true);
        EncryptedText.setEditable(false);
        encryptionScrollPane.setViewportView(EncryptedText);
        KeyGenAndDecryptionPanel.add(encryptionScrollPane, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        EncryptionLabel = new JLabel();
        EncryptionLabel.setText(" ENCRYPTED TEXT (CIPHER TEXT) ");
        EncryptionLabel.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        KeyGenAndDecryptionPanel.add(EncryptionLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        DecryptedTextLabel = new JLabel();
        DecryptedTextLabel.setText(" DECRYPTED TEXT (PLAIN TEXT) ");
        DecryptedTextLabel.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        KeyGenAndDecryptionPanel.add(DecryptedTextLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        DecryptedText = new JTextArea();
        DecryptedText.setWrapStyleWord(true);
        DecryptedText.setLineWrap(true);
        DecryptedText.setEditable(false);
        KeyGenAndDecryptionPanel.add(DecryptedText, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));


        /**
         * ACTION PERFORM PART
         */
        keyList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                keyList.getSelectedIndex();
            }
        });
        keyList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        createKeyPairButton.addMouseListener(new MouseAdapter() {
            @SuppressWarnings("serial")
            public void mouseClicked(MouseEvent e) {
                KeyGenerator keyGenerator = new KeyGenerator();
                keys.add(keyGenerator);
                ArrayList<String> keyStrings = new ArrayList<>();
                int i = 1;
                for (KeyGenerator key : keys) {
                    keyStrings.add(i + "=> PUBLIC KEY: " + "(" + key.getPublicKey() + "," + key.getModulus() + ")" + "  => PRIVATE KEY: (" + key.getPrivateKey() + "," + key.getModulus() + ")");
                    defaultListModel1.removeAllElements();
                    defaultListModel1.addAll(keyStrings);
                    i++;
                }
            }
        });


        encryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String input = enterTextToEncryptTextField.getText();
                int index = keyList2.getSelectedIndex();
                selected_index = keyList2.getSelectedIndex();
                if (index < 0) {
                    JOptionPane.showMessageDialog(encryptButton, "You need to enter a text to Encrypt! or You need to" + " " + "choose " + "Key Pair!");
                    return;
                }

                KeyGenerator keyPairs = keys.get(index);
                try {
                    encryption = new Encryption(keyPairs.getPublicKey(), keyPairs.getModulus(), input);
                } catch (Exception error) {
                    throw new RuntimeException(error);
                }

                EncryptedText.setText(encryption.getParsedString());
            }
        });


        decryptButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (encryption == null) {
                    JOptionPane.showMessageDialog(decryptButton, "First " + "you need to encrypt a text before decryption");
                    return;
                }
                int index = keyList2.getSelectedIndex();
                try {
                    if (index != selected_index) {
                        JOptionPane.showMessageDialog(decryptButton, "Please Choose Appropriate Private Key!!");
                        flag = 1;
                        return;
                    } else {
                        KeyGenerator key = keys.get(index);
                        System.out.println(key);
                        Decryption decryption = new Decryption(key.getPrivateKey(), key.getModulus(), encryption.getBlockArray(), encryption.checkIsOddLength());
                        DecryptedText.setText(decryption.getDecryptedText());
                    }
                } catch (Exception error) {
                    System.out.println(error);
                    JOptionPane.showMessageDialog(decryptButton, "An Error has Occurred, Please try again!!");
                }
            }
        });
    }
}
