/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game_puzzle_2048;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author emilki
 */
public class Game_puzzle_2048 extends JFrame implements KeyListener, ActionListener {
    public Random rand = new Random();
    public Scanner sc = new Scanner(System.in);
    public int[][] game_table = new int[4][4];
    public int[][] previous_game_table = new int[4][4];
    public int[][] previous_2_game_table = new int[4][4];
    public JFrame frame = new JFrame();
    public JPanel[][] game_panels = new JPanel[4][4];
    public JLabel[][] values = new JLabel[4][4];
    public JLabel help_text = new JLabel("WASD to move, R to reset, BACKSPACE to return 1 move");
    public JButton[] game_inputs = new JButton[4];
    public int moves = 0;
    public boolean can_press = false;

    public Game_puzzle_2048() {
        frame.setTitle("2048");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setResizable(false);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                values[i][j] = new JLabel(" ");
                values[i][j].setFont(new Font("Serif", Font.PLAIN, 34));
                values[i][j].setBounds(235 + i * 85 + 34, 235 + j * 85 + 34, 40, 40);
                game_panels[i][j] = new JPanel();
                game_panels[i][j].setBounds(235 + i * 85, 150 + j * 85 + 4, 80, 80);
                game_panels[i][j].add(values[i][j]);
                frame.add(game_panels[i][j]);
            }
        }
        help_text.setForeground(Color.white);
        help_text.setBounds(245,500,500,50);
        frame.add(help_text);
        frame.setVisible(true);
        JPanel back = new JPanel();
        back.setBackground(new Color(75, 75, 75));
        back.setLocation(0, 0);
        back.setSize(800, 800);
        // game_inputs = new JButton();
        // game_inputs.setBounds(400, 550, 80, 80);
        frame.add(back);
        frame.addKeyListener(this);
        frame.setFocusable(true);
        game_start();
    }

    public int rand_2_4() {
        int n = rand.nextInt(4);
        if (n < 3)
            return 2;
        else
            return 4;
    }

    public void game_display() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int n = game_table[i][j] + 1;
                if(game_table[j][i]!=0){
                    values[i][j].setText(String.format("%1d", game_table[j][i]));
                }else{
                    values[i][j].setText("");
                }
                game_panels[i][j].setBackground(new Color(Math.max(255 - (int)(Math.log(game_table[j][i]) / Math.log(2)) * 30, 0), 255, 255));
                while (n < 10000) {
                    n = n * 10;
                }
            }
        }
    }

    public void game_start() {
        game_table[rand.nextInt(4)][rand.nextInt(4)] = rand_2_4();
        int z = rand.nextInt(3), z1 = rand.nextInt(3);
        while (game_table[z][z1] != 0) {
            z = rand.nextInt(3);
            z1 = rand.nextInt(3);
        }
        game_table[z][z1] = rand_2_4();
        game_display();
        game_input();
    }

    public void up_1_3_step() {
        int br = 0;
        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                if (game_table[i][j] != 0) {
                    if (i > 0 && game_table[i - 1][j] == 0) {
                        game_table[i - 1][j] = game_table[i][j];
                        game_table[i][j] = 0;
                        br++;
                        moves++;
                    }
                }
            }
        }
        if (br != 0)
            up_1_3_step();
    }

    public void up_2_step() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i != 3 && game_table[i][j] == game_table[i + 1][j] && game_table[i][j] != 0) {
                    game_table[i][j] = game_table[i][j] + game_table[i + 1][j];
                    game_table[i + 1][j] = 0;
                    moves++;
                }
            }
        }
        if (moves != 0) {
            up_1_3_step();
            moves = 0;
            number_add();
        } else
            up_1_3_step();
    }

    public void left_1_3_step() {
        int br = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 3; i >= 0; i--) {
                if (game_table[j][i] != 0) {
                    if (i != 0 && game_table[j][i - 1] == 0) {
                        game_table[j][i - 1] = game_table[j][i];
                        game_table[j][i] = 0;
                        br++;
                        moves++;
                    }
                }
            }
        }
        if (br != 0)
            left_1_3_step();

    }

    public void left_2_step() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i != 3 && game_table[j][i] == game_table[j][i + 1] && game_table[j][i] != 0) {
                    game_table[j][i] = game_table[j][i] + game_table[j][i + 1];
                    game_table[j][i + 1] = 0;
                    moves++;
                }
            }
        }
        if (moves != 0) {
            left_1_3_step();
            moves = 0;
            number_add();
        } else
            left_1_3_step();
    }

    public void down_1_3_step() {
        int br = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (game_table[i][j] != 0) {
                    if (i < 3 && game_table[i + 1][j] == 0) {
                        game_table[i + 1][j] = game_table[i][j];
                        game_table[i][j] = 0;
                        br++;
                        moves++;
                    }
                }
            }
        }
        if (br != 0)
            down_1_3_step();
    }

    public void down_2_step() {
        for (int i = 3; i >= 0; i--) {
            for (int j = 3; j >= 0; j--) {
                if (i != 0 && game_table[i][j] == game_table[i - 1][j] && game_table[i][j] != 0) {
                    game_table[i][j] = game_table[i][j] + game_table[i - 1][j];
                    game_table[i - 1][j] = 0;
                    moves++;
                }
            }
        }
        if (moves != 0) {
            down_1_3_step();
            moves = 0;
            number_add();
        } else
            down_1_3_step();
    }

    public void right_1_3_step() {
        int br = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (game_table[i][j] != 0) {
                    if (j != 3 && game_table[i][j + 1] == 0) {
                        game_table[i][j + 1] = game_table[i][j];
                        game_table[i][j] = 0;
                        br++;
                        moves++;
                    }
                }
            }
        }
        if (br != 0)
            right_1_3_step();
    }

    public void right_2_step() {
        for (int i = 0; i < 4; i++) {
            for (int j = 3; j >= 0; j--) {
                if (j != 0 && game_table[i][j] == game_table[i][j - 1] && game_table[i][j] != 0) {
                    game_table[i][j] = game_table[i][j - 1] + game_table[i][j];
                    game_table[i][j - 1] = 0;
                    moves++;
                }
            }
        }
        if (moves != 0) {
            right_1_3_step();
            moves = 0;
            number_add();
        } else
            right_1_3_step();
    }

    public void back() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(!is_equal(previous_game_table, game_table)){
                    if(!is_zero(previous_game_table)){
                        game_table[i][j] = previous_game_table[i][j];
                        previous_game_table[i][j] = previous_game_table[i][j];                
                    }
                }else{
                    if(!is_zero(previous_2_game_table)){
                        game_table[i][j] = previous_2_game_table[i][j];
                        previous_game_table[i][j] = previous_2_game_table[i][j];
                    }
                }
            }
        }
        game_display();
        game_input();
    }

    public boolean is_zero(int arr1[][]){
        boolean res=true;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(arr1[i][j]!=0){
                    res=false;
                }
            }        
        }
        return res;
    }    
    
    public boolean is_equal(int arr1[][],int arr2[][]){
        boolean res=true;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(arr1[i][j]!=arr2[i][j]){
                    res=false;
                }
            }        
        }
        return res;
    }
    
    public void previous_game_table() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(!is_equal(previous_game_table, game_table)){
                    previous_2_game_table[i][j] = previous_game_table[i][j];
                }
                previous_game_table[i][j] = game_table[i][j];
                System.out.print(previous_game_table[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void reset() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                game_table[i][j] = 0;
            }
        }
        game_start();
    }

    public void number_add() {
        int[] empty_spaces = new int[32];
        int br = 0, a;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (game_table[i][j] == 0) {
                    empty_spaces[br] = i;
                    empty_spaces[br + 1] = j;
                    br = br + 2;
                }
            }
        }
        a = rand.nextInt(br / 2);
        game_table[empty_spaces[(a) * 2]][empty_spaces[a * 2 + 1]] = rand_2_4();
    }

    public void game_input() {
        can_press = true;
    }

    public static void main(String[] args) {
        new Game_puzzle_2048();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (can_press == false) {
            return;
        }
        if (key == KeyEvent.VK_W) {
            can_press = false;
            previous_game_table();
            up_1_3_step();
            up_2_step();
            game_display();
            game_input();
        }
        if (key == KeyEvent.VK_A) {
            can_press = false;
            previous_game_table();
            left_1_3_step();
            left_2_step();
            game_display();
            game_input();
        }
        if (key == KeyEvent.VK_S) {
            can_press = false;
            previous_game_table();
            down_1_3_step();
            down_2_step();
            game_display();
            game_input();
        }
        if (key == KeyEvent.VK_D) {
            can_press = false;
            previous_game_table();
            right_1_3_step();
            right_2_step();
            game_display();
            game_input();
        }
        if (key == KeyEvent.VK_R) {
            can_press = false;
            previous_game_table();
            reset();
        }        
        if (key == KeyEvent.VK_BACK_SPACE) {
            can_press = false;
            back();
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String a = e.getActionCommand();
        if(a.equals("help")){
            
        }
    }
}