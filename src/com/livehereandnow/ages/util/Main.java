package com.livehereandnow.ages.util;

import entity.AgesCard;
import entity.AgesCardJpaController;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    EntityManagerFactory emf;
    AgesCardJpaController control;
    AgesCard card;

    public Main() {
        emf = Persistence.createEntityManagerFactory("agesPU");
        control = new AgesCardJpaController(emf);

    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
//        main.showEffect();
//        main.showIconPoints();

        main.updateEffectAndIconPoints();

        System.out.println("...after...");
        main.showEffect();
        main.showIconPoints();

    }

    void parseCardEffect() {
        String[] tokens = card.getEffect().split(";");
        for (String token : tokens) {
            if (token.trim().length() == 0) {
                continue;
            }
//            System.out.println(""+token);
            String[] pair = token.split(":");
            String key = pair[0];
            int val = Integer.parseInt(pair[1]);
//            System.out.println(key + " " + val);

//            Effect keyword: [+黃點, +白點, +紅點, 石頭, 燈泡, 笑臉, 食物, 過期武器, +房屋, 音樂, +藍點, 武器]
            switch (key) {
                case "+黃點":
                    card.setEffectYellow(val);
                    break;
                case "+白點":
                    card.setEffectWhite(val);
                    break;
                case "+紅點":
                    card.setEffectRed(val);
                    break;
                case "+藍點":
                    card.setEffectBlue(val);
                    break;
                case "+房屋":
                    card.setEffectHouse(val);
                    break;
                case "食物":
                    card.setEffectFood(val);
                    break;
                case "音樂":
                    card.setEffectMusic(val);
                    break;
                case "石頭":
                    card.setEffectStone(val);
                    break;
                case "燈泡":
                    card.setEffectIdea(val);
                    break;
                case "笑臉":
                    card.setEffectSmile(val);
                    break;
                case "藍點":
                    card.setEffectBlue(val);
                    break;
                case "武器":
                    card.setEffectWeapon(val);
                    break;
                case "過期武器":
                    card.setEffectWeaponOld(val);
                    break;
                default:
                    System.out.println("***************NEED TO HANDLE => " + key);

            }

        }
    }

    void parseIconPoints() {
//        int counter=0;
        String[] tokens = card.getIconPoints().split(";");
        for (String token : tokens) {
            if (token.trim().length() == 0) {
                continue;
            }
//            System.out.println(""+token);
            String[] pair = token.split(":");
            String key = pair[0];
            int val = -999;
            try {
                if (key.equals("奇蹟石頭")) {
                    val = 98765;
                } else {

                    val = Integer.parseInt(pair[1]);
                }
            } catch (Exception ex) {
                System.out.println(card.getId() + " IconPoints is " + card.getIconPoints());

                System.out.println("token is ###" + token + "###");
                ex.printStackTrace();
                System.exit(-1);
            }
//            System.out.println(key + " " + val);

//            Effect keyword: [+黃點, +白點, +紅點, 石頭, 燈泡, 笑臉, 食物, 過期武器, +房屋, 音樂, +藍點, 武器]
            switch (key) {
                case "擴充人口":
                    card.setCostPeople(val);
                    break;
                case "步":
                    card.setCostFoot(val);
                    break;
                case "奇蹟石頭":
                    card.setCostWonder(val);
                    break;
                case "軍事牌":
                    card.setCostMilitary(val);
                    break;
                case "馬":
                    card.setCostHorse(val);
                    break;
                case "炮":
                    card.setCostCannon(val);
                    break;
                case "和平燈炮":
                    card.setCostPeace(val);
                    break;
                case "革命燈泡":
                    card.setCostRevolution(val);
                    break;
                case "消耗紅點":
                    card.setCostRed(val);
                    break;
                case "石頭":
                    card.setCostStone(val);
                    break;
                case "燈泡":
                    card.setCostIdea(val);
                    break;
                case "食物":
                    card.setCostFood(val);
                    break;
                case "音樂":
                    card.setCostMusic(val);
                    break;

                default:
                    System.out.println("***************NEED TO HANDLE => " + key);
                    System.exit(-1);
            }
        }
    }

    private void updateEffectAndIconPoints() throws Exception {
        List<AgesCard> list = control.findAgesCardEntities();
        for (AgesCard x : list) {
            card = x;
            if (card.getEffect().length() > 0) {
                parseCardEffect();
            }
            if (card.getIconPoints().length() > 0) {
                parseIconPoints();
            }

            control.edit(card);
        }

    }

    private void showEffect() {
        List<AgesCard> list = control.findAgesCardEntities();
        for (AgesCard x : list) {
            card = x;
            if (card.getEffect().length() > 0) {
//            System.out.print("SEQ:" + card.getSeq());
                System.out.print(" " + card.getId());
                System.out.print(" " + card.getName());
                System.out.println("  EFFECT:" + card.getEffect());
                // [擴充人口, 步, 奇蹟石頭, 軍事牌, 馬, 炮, 和平燈炮, 革命燈泡, 消耗紅點, 石頭, 燈泡, 食物, 音樂]  
                //[+黃點, +白點, +紅點, 石頭, 燈泡, 笑臉, 食物, 過期武器, +房屋, 音樂, +藍點, 武器]
                if (card.getEffectBlue() > 0) {
                    System.out.println("  +藍點 " + card.getEffectBlue());
                }
                if (card.getEffectFood() > 0) {
                    System.out.println("  食物" + card.getEffectFood());
                }
                if (card.getEffectHouse() > 0) {
                    System.out.println("  +房屋" + card.getEffectHouse());
                }
                if (card.getEffectMusic() > 0) {
                    System.out.println("  音樂" + card.getEffectMusic());
                }
                if (card.getEffectRed() > 0) {
                    System.out.println("  +紅點" + card.getEffectRed());
                }
                if (card.getEffectSmile() > 0) {
                    System.out.println("  笑臉" + card.getEffectSmile());
                }
                if (card.getEffectStone() > 0) {
                    System.out.println("  石頭" + card.getEffectStone());
                }
                if (card.getEffectWeapon() > 0) {
                    System.out.println("  武器" + card.getEffectWeapon());
                }
                if (card.getEffectWeaponOld() > 0) {
                    System.out.println("  過期武器" + card.getEffectWeaponOld());
                }
                if (card.getEffectWhite() > 0) {
                    System.out.println(" +白點 " + card.getEffectWhite());
                }
                if (card.getEffectYellow() > 0) {
                    System.out.println("  +黃點" + card.getEffectYellow());
                }

            }
        }

    }

    private void showIconPoints() {
        List<AgesCard> list = control.findAgesCardEntities();
        for (AgesCard x : list) {
            card = x;
            if (card.getIconPoints().length() > 0) {
//            System.out.print("SEQ:" + card.getSeq());
                System.out.print(" " + card.getId());
                System.out.print(" " + card.getName());
                System.out.println("  ICON POINTS:" + card.getIconPoints());
                // [擴充人口, 步, 奇蹟石頭, 軍事牌, 馬, 炮, 和平燈炮, 革命燈泡, 消耗紅點, 石頭, 燈泡, 食物, 音樂]  
                //[+黃點, +白點, +紅點, 石頭, 燈泡, 笑臉, 食物, 過期武器, +房屋, 音樂, +藍點, 武器]
                System.out.print(" " + card.getCostCannon());
                System.out.print(" " + card.getCostFood());
                System.out.print(" " + card.getCostFoot());
                System.out.print(" " + card.getCostHorse());
                System.out.print(" " + card.getCostIdea());
                System.out.print(" " + card.getCostMilitary());
                System.out.print(" " + card.getCostPeace());
                System.out.print(" " + card.getCostPeople());
                System.out.print(" " + card.getCostRed());
                System.out.print(" " + card.getCostRevolution());
                System.out.print(" " + card.getCostStone());
                System.out.print(" " + card.getCostWonder());
                System.out.println("");

            }
        }

    }
}
