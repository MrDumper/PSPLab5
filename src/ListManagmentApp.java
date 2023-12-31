import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class ListManagmentApp extends JFrame{
    private DefaultListModel<String> surnameListModel;
    private DefaultListModel<String> nameListModel;
    private DefaultListModel<String> patronymicListModel;
    private DefaultListModel<String> sortedListModel;
    private JList<String> surnameList;
    private JList<String> nameList;
    private JList<String> patronymicList;
    private JTextField inputField;
    private JTextField reasonField;
    private JTextField doctorsField;

    public ListManagmentApp() {
        super("Управление списками");

        // Создание списков
        surnameListModel = new DefaultListModel<>();
        nameListModel = new DefaultListModel<>();
        patronymicListModel = new DefaultListModel<>();
        sortedListModel = new DefaultListModel<>();
        surnameList = new JList<>(surnameListModel);
        nameList = new JList<>(nameListModel);
        patronymicList = new JList<>(patronymicListModel);

        // Создание компонентов ввода
        inputField = new JTextField();
        reasonField = new JTextField();
        doctorsField = new JTextField();

        // Создание кнопки
        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDialog();
            }
        });

        // Создание панели с кнопкой
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

        // Создание панели с компонентами ввода
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Фамилия:"));
        inputPanel.add(inputField);
        inputPanel.add(new JLabel("Причина посещения:"));
        inputPanel.add(reasonField);
        inputPanel.add(new JLabel("Необходимые врачи:"));
        inputPanel.add(doctorsField);

        // Создание панели со списками
        JPanel listPanel = new JPanel(new GridLayout(1, 3));
        listPanel.add(new JScrollPane(surnameList));
        listPanel.add(new JScrollPane(nameList));
        listPanel.add(new JScrollPane(patronymicList));

        // Создание основной панели и компоновка элементов
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(listPanel, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Установка основной панели в окне
        setContentPane(mainPanel);

        // Настройка параметров окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
    }

    private void showDialog() {
        String input = JOptionPane.showInputDialog(this, "Введите информацию в формате \"Фамилия Имя Отчество\"");
        if (input != null) {
            String[] parts = input.split(" ");
            if (parts.length == 3) {
                String surname = parts[0];
                String name = parts[1];
                String patronymic = parts[2];
                String reason = reasonField.getText();
                String doctors = doctorsField.getText();

                surnameListModel.addElement(surname);
                nameListModel.addElement(name);
                patronymicListModel.addElement(patronymic);

                // Обновление отсортированного списка
                updateSortedListModel();

                // Очистка полей ввода
                inputField.setText("");
                reasonField.setText("");
                doctorsField.setText("");

                // Вывод информации о бронировании
                JOptionPane.showMessageDialog(this, "Талон успешно забронирован!\n" +
                        "Причина посещения: " + reason + "\n" +
                        "Необходимые врачи: " + doctors, "Бронирование талона", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Неверный формат ввода!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateSortedListModel() {
        sortedListModel.clear();

        List<String> surnames = new ArrayList<>();
        for (int i = 0; i < surnameListModel.size(); i++) {
            surnames.add(surnameListModel.getElementAt(i));
        }
        Collections.sort(surnames);

        for (String surname : surnames) {
            int index = surnameListModel.indexOf(surname);
            String name = nameListModel.getElementAt(index);
            String patronymic = patronymicListModel.getElementAt(index);
            sortedListModel.addElement(surname + " " + name + " " + patronymic);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListManagmentApp();
            }
        });
    }
}
