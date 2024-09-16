import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WhoIsItGame {

    private int selectedQuestionIndex = -1;
    private Map<String, Boolean[]> characters;
    private Map<String, String> characterImages;
    private JFrame mainFrame;
    private JPanel characterPanel;
    private JButton startButton;
    private JButton checkButton;
    private JLabel logoLabel;
    private JLabel notificationLabel;
    private JLabel selectedCharacterImageLabel;
    private JLabel selectedCharacterNameLabel;
    private String selectedCharacterByComputer;
    private JButton startGameButton;

    private void selectCharacterByComputer() {
        if (selectedCharacterByComputer == null) {
            Object[] characterNames = characters.keySet().toArray();
            selectedCharacterByComputer = (String) characterNames[new Random().nextInt(characterNames.length)];

            JOptionPane.showMessageDialog(mainFrame, "Bilgisayar karakterini seçti.");
        }
    }

    private boolean answerQuestionByComputer(int questionIndex) {
        return characters.get(selectedCharacterByComputer)[questionIndex];
    }

    private String firstSelectedCharacter = null;

    private void guessCharacter(String guessedCharacter) {
        if (guessedCharacter.equals(selectedCharacterByComputer)) {
            JOptionPane.showMessageDialog(mainFrame, "Tebrikler! Doğru karakteri tahmin ettiniz: " + guessedCharacter);

        } else {
            JOptionPane.showMessageDialog(mainFrame, "Yanlış tahmin! Tekrar deneyin.");
        }
    }

    public WhoIsItGame() {

        characters = new HashMap<>();
        characterImages = new HashMap<>();

        initializeCharacters();
        defineCharacterImagePaths();

        initializeGui();

        startGameButton = new JButton("Oyuna Başla");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToGameScreen();
            }
        });

        {
        }

        notificationLabel = new JLabel();
        notificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainFrame.add(notificationLabel, BorderLayout.NORTH);

        selectedCharacterImageLabel = new JLabel();
        selectedCharacterImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainFrame.add(selectedCharacterImageLabel, BorderLayout.EAST);

    }

    private JLabel lastSelectedLabel = null;

    private boolean isCharacterSelected = false;

    private void selectCharacter(String characterName, JLabel selectedLabel) {
        if (isCharacterSelected) {

            return;
        }

        notificationLabel.setText("Seçilen karakter: " + characterName);
        JOptionPane.showMessageDialog(mainFrame, "Seçtiğin karakter: " + characterName);

        firstSelectedCharacter = characterName;
        selectedLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
        lastSelectedLabel = selectedLabel;

        isCharacterSelected = true;

        selectCharacterByComputer();

        askQuestionByComputer();

        if (firstSelectedCharacter == null) {
            firstSelectedCharacter = characterName;
            selectedLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
            lastSelectedLabel = selectedLabel;
        }

        selectedCharacterNameLabel.setText("Seçtiğiniz karakter: " + characterName);
    }

    private void showEliminatedCharactersTemporarily(int milliseconds) {
        JFrame tempFrame = new JFrame("Elenen Karakterler");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (Component comp : characterPanel.getComponents()) {
            if (comp instanceof JLabel && !comp.isVisible()) {
                String characterName = ((JLabel) comp).getToolTipText();
                panel.add(new JLabel(characterName));
            }
        }

        tempFrame.getContentPane().add(new JScrollPane(panel));
        tempFrame.pack();
        tempFrame.setVisible(true);

        new Timer(milliseconds, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempFrame.dispose();
            }
        }).start();
    }

    private void initializeCharacters() {
        characters = new HashMap<>();
        // Özellik sırası: 1)Sarı saçlı, 2)Kahverengi saçlı, 3)Siyah saçlı, 4)Sakallı,
        // 5)Bandana, 6)Bant, 7)Küpe, 8)Kravat, 9)Kolye, 10)Ağzı açık, 11)Erkek,
        // 12)Gözlük, 13)Bıyık, 14)Tavşan kulağı, 15)Kel
        characters.put("Chantal", new Boolean[] { false, true, false, false, false, false, true, false, true, true,
                false, false, false, false, false });
        characters.put("Eric", new Boolean[] { false, false, false, true, false, false, false, false, false, false,
                true, false, true, true, true });
        characters.put("Alex", new Boolean[] { true, false, false, false, false, false, false, true, false, true, true,
                false, false, false, false });
        characters.put("Bob", new Boolean[] { true, false, false, true, false, false, false, false, false, false, true,
                false, false, true, false });
        characters.put("Paul", new Boolean[] { false, false, false, false, true, false, false, false, false, false,
                true, false, true, false, false });
        characters.put("Frank", new Boolean[] { false, false, true, false, false, true, true, false, false, true, true,
                false, true, false, false });
        characters.put("Zoe", new Boolean[] { false, true, false, false, false, false, true, false, true, true, false,
                false, false, false, false });
        characters.put("Joe", new Boolean[] { false, false, false, false, false, false, false, false, false, false,
                true, true, false, false, true });
        characters.put("Buba", new Boolean[] { false, false, true, false, false, true, true, false, false, false, false,
                false, false, false, false });
        characters.put("Rita", new Boolean[] { false, false, false, false, false, false, true, true, true, false, false,
                true, false, false, false });
        characters.put("Rick", new Boolean[] { true, false, false, true, false, false, false, true, false, false, true,
                true, true, false, false });
        characters.put("Antoine", new Boolean[] { false, true, false, false, false, false, false, false, false, false,
                true, false, true, false, false });
        characters.put("John", new Boolean[] { false, false, true, false, false, false, false, false, false, false,
                true, true, false, false, false });
        characters.put("Chap", new Boolean[] { false, false, false, true, false, false, true, false, false, false, true,
                true, false, false, true });
        characters.put("Evelyn", new Boolean[] { false, false, false, false, false, false, true, false, true, false,
                false, false, false, false, false });
        characters.put("Lady", new Boolean[] { false, true, false, false, false, false, true, false, false, false,
                false, true, false, false, false });
        characters.put("Samantha", new Boolean[] { true, false, false, false, true, false, true, false, true, false,
                false, false, false, false, false });
        characters.put("Jenny", new Boolean[] { false, false, false, false, false, false, true, false, false, false,
                false, true, false, false, false });
        characters.put("Javier", new Boolean[] { false, false, true, false, false, true, false, false, false, true,
                true, false, true, false, false });
        characters.put("Evan", new Boolean[] { true, false, false, false, false, false, false, false, false, false,
                true, false, true, false, false });
        characters.put("Mathias", new Boolean[] { false, false, false, false, false, false, false, false, false, false,
                true, true, false, false, false });
        characters.put("Michael", new Boolean[] { true, false, false, false, false, false, true, true, false, false,
                true, false, false, false, false });
        characters.put("Hank", new Boolean[] { false, false, false, false, false, false, false, true, false, true, true,
                false, false, true, false });
        characters.put("Vito", new Boolean[] { false, false, false, true, false, false, false, false, false, false,
                true, false, false, false, true });

    }

    private void showRemainingCharactersTemporarily(int milliseconds) {
        JFrame tempFrame = new JFrame("Kalan Karakterler");
        JPanel remainingPanel = new JPanel();
        remainingPanel.setLayout(new BoxLayout(remainingPanel, BoxLayout.Y_AXIS));

        for (Component comp : characterPanel.getComponents()) {
            if (comp instanceof JLabel && comp.isVisible()) {
                JLabel label = (JLabel) comp;
                ImageIcon icon = new ImageIcon(((ImageIcon) label.getIcon()).getDescription());
                JLabel newLabel = new JLabel(icon);
                newLabel.setToolTipText(label.getToolTipText());
                remainingPanel.add(newLabel);
            }
        }

        tempFrame.add(new JScrollPane(remainingPanel));
        tempFrame.pack();
        tempFrame.setVisible(true);

        new Timer(milliseconds, e -> tempFrame.dispose()).start();

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isCharacterSelected) {
                    JOptionPane.showMessageDialog(mainFrame, "Önce karakter seçmelisiniz");
                    return;
                }
                if (selectedQuestionIndex != -1) {
                    filterCharacters(selectedQuestionIndex, answerQuestionByComputer(selectedQuestionIndex));
                    showRemainingCharactersTemporarily(5000);
                }
            }
        });
    }

    private JComboBox<String> questionComboBox;

    private void initializeGui() {

        mainFrame = new JFrame("Who is it?");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setVisible(true);

        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());
        logoLabel = new JLabel(new ImageIcon("c:/Users/Fatih Eskici/Desktop/javagorseller/logo.png"),
                SwingConstants.CENTER);

        logoLabel.setPreferredSize(new Dimension(800, 540));

        startButton = new JButton("Başla");
        startButton.setPreferredSize(new Dimension(100, 50));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(startButton);

        startPanel.add(logoLabel, BorderLayout.CENTER);
        startPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainFrame.add(startPanel);

        characterPanel = new JPanel(new GridLayout(4, 6));

        addCharactersToPanel();

        mainFrame.setVisible(true);

        selectedCharacterNameLabel = new JLabel();
        selectedCharacterNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainFrame.add(selectedCharacterNameLabel, BorderLayout.NORTH);

    }

    private void addCharactersToPanel() {

        for (Map.Entry<String, String> entry : characterImages.entrySet()) {
            String characterName = entry.getKey();
            String imagePath = entry.getValue();

            ImageIcon icon = new ImageIcon("c:/Users/Fatih Eskici/Desktop/javagorseller/" + imagePath);
            JLabel characterLabel = new JLabel(icon);

            characterLabel.setToolTipText(characterName);

            characterLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!isCharacterSelected) {
                        System.out.println("Karakter seçildi: " + characterName);
                        selectCharacter(characterName, characterLabel);
                    }
                }
            });

            characterPanel.add(characterLabel);
        }
    }

    public static void initializeGame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WhoIsItGame();
            }
        });
    }

    private int currentQuestionIndex = 0;
    private JButton prevButton, nextButton;
    private JLabel questionLabel;

    private String[] questions = {
            "Karakterin sarı saçlı mı?",
            "Karakterin kahverengi saçlı mı?",
            "Karakterin siyah saçlı mı?",
            "Karakterin sakallı mı?",
            "Karakterin bandana takıyor mu?",
            "Karakterinin saçında bant var mı?",
            "Karakterin küpe takıyor mu?",
            "Karakterin kravat takıyor mu?",
            "Karakterin kolye takıyor mu?",
            "Karakterin ağzı açık mı?",
            "Karakterin erkek mi?",
            "Karakterin gözlük takıyor mu?",
            "Karakterin bıyıklı mı?",
            "Karakterin tavşan kulağı mı takıyor?",
            "Karakterin kel mi?"

    };

    private void initializeQuestionMechanism() {

        questionComboBox = new JComboBox<>(questions);

        prevButton = new JButton("<");
        nextButton = new JButton(">");
        checkButton = new JButton("Soruyu seç");
        questionLabel = new JLabel(questions[currentQuestionIndex], SwingConstants.CENTER);

        checkButton.setPreferredSize(new Dimension(100, 50));
        prevButton.setPreferredSize(new Dimension(100, 50));
        nextButton.setPreferredSize(new Dimension(100, 50));
        questionLabel.setPreferredSize(new Dimension(250, 50));
        startGameButton.setPreferredSize(new Dimension(120, 50));
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!isCharacterSelected) {
                    JOptionPane.showMessageDialog(mainFrame, "Önce karakter seçmelisiniz");
                    return;
                }

                if (selectedQuestionIndex != -1) {
                    filterCharacters(selectedQuestionIndex, answerQuestionByComputer(selectedQuestionIndex));
                    showRemainingCharactersTemporarily(5000);
                }

            }
        });

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isCharacterSelected) {
                    JOptionPane.showMessageDialog(mainFrame, "Önce karakter seçmelisiniz");
                    return;
                }

                if (selectedQuestionIndex != -1) {
                    boolean answer = answerQuestionByComputer(selectedQuestionIndex);
                    filterCharacters(selectedQuestionIndex, answer);

                    showRemainingCharactersTemporarily(5000);
                }
            }
        });

        prevButton.addActionListener(e -> navigateQuestions(-1));
        nextButton.addActionListener(e -> navigateQuestions(1));

        JPanel questionPanel = new JPanel(new FlowLayout());
        questionPanel.setPreferredSize(new Dimension(mainFrame.getWidth(), 125));

        questionPanel.add(questionComboBox);
        questionPanel.add(checkButton);
        questionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        questionPanel.add(prevButton);
        questionPanel.add(questionLabel);
        questionPanel.add(nextButton);
        questionPanel.add(checkButton);
        questionPanel.add(questionComboBox);
        mainFrame.add(questionPanel, BorderLayout.NORTH);
        mainFrame.add(questionPanel, BorderLayout.SOUTH);
        mainFrame.add(questionPanel, BorderLayout.PAGE_END);

    }

    private void showGameScreen() {
        SwingUtilities.invokeLater(new Runnable());
    }

    private void showEliminatedCharacters() {
        if (eliminatedFrame == null) {
            eliminatedFrame = new JFrame("Elenen Karakterler");
            eliminatedFrame.setSize(300, 400);
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (Component comp : characterPanel.getComponents()) {
            if (comp instanceof JLabel && !comp.isVisible()) {
                String characterName = ((JLabel) comp).getToolTipText();
                panel.add(new JLabel(characterName));
            }
        }

        eliminatedFrame.getContentPane().removeAll();
        eliminatedFrame.getContentPane().add(new JScrollPane(panel));
        eliminatedFrame.revalidate();
        eliminatedFrame.repaint();
        eliminatedFrame.setVisible(true);
    }

    private void updateEliminatedCharactersDisplay() {
        showEliminatedCharacters();

    }

    @Override
    public void run() {
        mainFrame.getContentPane().removeAll();
        mainFrame.add(characterPanel);
        mainFrame.setSize(new Dimension(1500, 1000));

        initializeQuestionMechanism();
        mainFrame.revalidate();
        mainFrame.repaint();

    }

    private void navigateQuestions(int direction) {
        currentQuestionIndex += direction;
        if (currentQuestionIndex < 0)
            currentQuestionIndex = questions.length - 1;
        else if (currentQuestionIndex >= questions.length) {
            currentQuestionIndex = 0;
        }
        questionLabel.setText(questions[currentQuestionIndex]);
        selectedQuestionIndex = currentQuestionIndex;

    }

    private void resetGame() {
        for (Component comp : characterPanel.getComponents()) {
            if (comp instanceof JLabel) {
                comp.setVisible(true);
            }
        }
        selectCharacterByComputer();

        if (lastSelectedLabel != null) {
            lastSelectedLabel.setBorder(null);
            lastSelectedLabel = null;
        }

    }

    private void checkWinCondition() {
        int visibleCharacters = 0;
        String lastVisibleCharacter = "";

        if (visibleCharacters == 1 && lastVisibleCharacter.equals(selectedCharacterByComputer)) {
            JOptionPane.showMessageDialog(new JFrame(), "Tebrikler! Kazandınız. Karakter: " + lastVisibleCharacter,
                    "Kazandınız!", JOptionPane.INFORMATION_MESSAGE);

            resetGame();
        }

        for (Component comp : characterPanel.getComponents()) {
            if (comp instanceof JLabel && comp.isVisible()) {
                visibleCharacters++;
                lastVisibleCharacter = ((JLabel) comp).getToolTipText();
            }
        }

        if (visibleCharacters == 1 && lastVisibleCharacter.equals(selectedCharacterByComputer)) {
            JOptionPane.showMessageDialog(mainFrame, "Tebrikler! Kazandınız. Karakter: " + lastVisibleCharacter);

        }
    }

    private void askQuestionByComputer() {
        int randomQuestionIndex = new Random().nextInt(questions.length);
        int response = JOptionPane.showConfirmDialog(
                mainFrame, questions[randomQuestionIndex],
                "Bilgisayarın Sorusu",
                JOptionPane.YES_NO_OPTION);
        boolean computerAnswer = answerQuestionByComputer(randomQuestionIndex);

        filterCharacters(randomQuestionIndex, computerAnswer);
        updateEliminatedCharactersDisplay();
        checkWinCondition();

    }

    private void answerQuestion(boolean playerAnswer) {
        boolean computerAnswer = answerQuestionByComputer(currentQuestionIndex);
        if (playerAnswer == computerAnswer) {
            filterCharacters(currentQuestionIndex, playerAnswer);
            checkWinCondition();
            updateEliminatedCharactersDisplay();
        } else {

        }
        askQuestionByComputer();

    }

    private void filterCharacters(int questionIndex, boolean answer) {
        for (Component comp : characterPanel.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                String characterName = label.getToolTipText();

                Boolean[] properties = characters.get(characterName);
                if (properties != null) {
                    if (properties[questionIndex] != answer) {
                        label.setVisible(false);
                    }
                }
            }
        }
    }

    private void loadCharacterImages() {
        for (String characterName : characters.keySet()) {
            ImageIcon characterImage = new ImageIcon(
                    "c:/Users/Fatih Eskici/Desktop/javagorseller/" + characterName + ".png");
            JLabel characterLabel = new JLabel(characterImage);
            characterPanel.add(characterLabel);
        }
    }

    private void setupButtonActions() {

        prevButton.addActionListener(e -> navigateQuestions(-1));

        nextButton.addActionListener(e -> navigateQuestions(1));

        yesButton.addActionListener(e -> answerQuestion(true));
        answerQuestion(true);
        askQuestionByComputer();

        noButton.addActionListener(e -> answerQuestion(false));
        answerQuestion(false);
        askQuestionByComputer();

    }

    private void switchToGameScreen() {
        mainFrame.getContentPane().removeAll();
        mainFrame.setSize(new Dimension(1400, 1000));
        mainFrame.add(characterPanel, BorderLayout.CENTER);
        initializeQuestionMechanism();
        mainFrame.revalidate();
        mainFrame.repaint();

        startGameButton.setPreferredSize(new Dimension(120, 50));
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    private void switchToStartScreen() {
        mainFrame.getContentPane().removeAll();
        mainFrame.add(startPanel);
        mainFrame.revalidate();
        mainFrame.repaint();

    }

    private void defineCharacterImagePaths() {
        characterImages.put("Chantal", "chantal.png");
        characterImages.put("Eric", "eric.png");
        characterImages.put("Alex", "alex.png");
        characterImages.put("Bob", "bob.png");
        characterImages.put("Paul", "paul.png");
        characterImages.put("Frank", "frank.png");
        characterImages.put("Zoe", "zoe.png");
        characterImages.put("Joe", "joe.png");
        characterImages.put("Buba", "buba.png");
        characterImages.put("Rita", "rita.png");
        characterImages.put("Rick", "rick.png");
        characterImages.put("Antoine", "antoine.png");
        characterImages.put("John", "john.png");
        characterImages.put("Chap", "chap.png");
        characterImages.put("Evelyn", "evelyn.png");
        characterImages.put("Lady", "lady.png");
        characterImages.put("Samantha", "samantha.png");
        characterImages.put("Jenny", "jenny.png");
        characterImages.put("Javier", "javier.png");
        characterImages.put("Evan", "evan.png");
        characterImages.put("Mathias", "mathias.png");
        characterImages.put("Michael", "michael.png");
        characterImages.put("Hank", "hank.png");
        characterImages.put("Vito", "vito.png");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                WhoIsItGame game = new WhoIsItGame();
                game.initializeCharacters();
                game.defineCharacterImagePaths();
                game.setupButtonActions();
            }
        });
    }
}
