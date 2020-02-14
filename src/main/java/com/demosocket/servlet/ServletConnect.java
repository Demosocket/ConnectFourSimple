package com.demosocket.servlet;

import com.demosocket.game.GameConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/game")
public class ServletConnect extends HttpServlet {
    private GameConnect gc;

    public ServletConnect() {
        gc = new GameConnect(6,7);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("play") == null) {
            gc.clearBoard();
        } else {
            int value = Integer.parseInt(req.getParameter("play"));
            if (value == -1) {
                gc.startOver();
                req.getRequestDispatcher("index.jsp").forward(req,resp);
                return;
            } else {
                gc.turn(value);
            }
        }
        if (gc.isGameOver()) {
            req.setAttribute("winner", gc.getWinner());
            req.setAttribute("tableStyle", "tr, td { border: 1px solid black;text-align:center;}");
        } else {
//            Нужно ли тут winner
            req.setAttribute("winner", gc.getWinner());
            req.setAttribute("tableStyle", "tr:not(:first-child) td { border: 1px solid black;text-align:center;}");
        }
        req.setAttribute("boardView", getBoard());
        req.getRequestDispatcher("/game.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        gc.setPlayer1Name(req.getParameter("name1"));
        gc.setPlayer2Name(req.getParameter("name2"));
        doGet(req,resp);
    }

    private String getBoard() {
        String view = "";
        view += "<table align=\"center\" style=\"width:70%\">";
        for (int i = 0; i < gc.getRowLength(); i++) {
            // render column buttons
            if (i == 0 && !gc.isGameOver()) {
                view += "<tr>";
                for (int j = 0; j < gc.getColLength(); j++) {
                    view += "<td>";
                    view += "<form action=\"game\" method=\"GET\">";
                    view += String.format(
                            "<button name=\"play\" value=\"%d\" style=\"text-align:center; width:64px; height:30px\">Move</button>",
                            j);
                    view += "</form>";
                    view += "</td>";
                }
                view += "</tr>";
            }
            // render board
            view += "<tr>";
            for (int j = 0; j < gc.getColLength(); j++) {
                view += String.format("<td><img alt=\"Coin\" src=%s></td>", gc.getImage(i, j));
            }
            view += "</tr>";
        }
        view += "</table>";
        return view;
    }
}
