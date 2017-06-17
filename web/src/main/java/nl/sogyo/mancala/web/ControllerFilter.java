package nl.sogyo.mancala.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.sogyo.mancala.Board;
import nl.sogyo.mancala.Computable;
import nl.sogyo.mancala.Player;
import nl.sogyo.mancala.computer.ComputerPlayer;
import nl.sogyo.mancala.exception.InvalidMoveException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class ControllerFilter implements Filter {

    private static Board board = null;

    private static List<Player> players = new ArrayList<Player>();

    private static final long serialVersionUID = -1980160738261905826L;

    private static String message = "";

    public static Board getBoard() {
        return board;
    }

    public static List<Player> getPlayers() {
        return players;
    }

    public void destroy() {
    }

    public boolean isComputable(Player player) {

        return player instanceof Computable;
    }

    public String getMessage() {

        String value = message;
        message = "";

        return value;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String page = "/index.vm";

        if (req.getRequestURI().indexOf(".vm") > 0) {

            if (req.getMethod().equals("POST")) {

                page = doPost(req, resp);

            } else if (req.getMethod().equals("GET")) {

                page = doGet(req, resp);
            }

            showPage(page, req, resp);

        } else {

            chain.doFilter(request, response);
        }
    }

    protected String doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        String page = "/index.vm";

        if (req.getRequestURI().contains("/index.vm")) {

            page = "/index.vm";
        }
        if (req.getRequestURI().contains("/new-game.vm")) {

            page = "/new-game.vm";
        }

        if (req.getRequestURI().contains("/play/")) {

            for (Player player : board.getPlayers()) {

                if (req.getRequestURI().contains(player.getName())) {

                    int index = getIdOfRequest(req);

                    try {

                        player.takeFromStore(index);

                    }
                    catch (InvalidMoveException e) {

                        setMessage(e.getMessage());

                        page = "/index.vm";
                    }

                    break;
                }
            }
        }

        if (req.getRequestURI().contains("/player/remove/")) {

            String player = getIdStringOfRequest(req);

            players.remove(players.indexOf(new Player(player)));

            page = "/new-game.vm";
        }

        return page;
    }

    protected String doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        if (req.getRequestURI().endsWith("/new-game.vm/player/add")) {

            Player player = new Player(req.getParameter("name"));

            players.add(player);

            return "/new-game.vm";
        }

        if (req.getRequestURI().endsWith("/index.vm/start")) {

            int beads = Integer.parseInt(req.getParameter("beads"));
            int stores = Integer.parseInt(req.getParameter("stores"));

            if (getPlayers().size() == 1) {

                getPlayers().add(new ComputerPlayer());

                setMessage("Computer player added...");
            }

            try {

                // start the game
                board = Board.create(stores, beads,
                    players.toArray(new Player[players.size()]));

            }
            catch (Exception e) {

                setMessage(e.getMessage());

                return "/new-game.vm";
            }

            return "/index.vm";
        }

        return "/new-game.vm";
    }

    private String getIdStringOfRequest(HttpServletRequest req) {

        String request = null;

        try {

            request = URLDecoder.decode(req.getRequestURI(), "UTF-8");

        }
        catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        return request.substring(1 + request.lastIndexOf('/'));
    }

    private int getIdOfRequest(HttpServletRequest req) {

        String request = null;

        try {

            request = URLDecoder.decode(req.getRequestURI(), "UTF-8");

        }
        catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        return Integer.parseInt(
            request.substring(1 + request.lastIndexOf('/')));
    }

    public void init(FilterConfig filterConfig) throws ServletException {

        try {

            Properties properties = new Properties();

            properties.load(this.getClass().getResourceAsStream(
                "/velocity.properties"));

            Velocity.init(properties);

        }
        catch (Exception e) {

            throw new ServletException();
        }
    }

    private void showPage(String page, HttpServletRequest req,
        HttpServletResponse resp) throws ServletException, IOException {

        VelocityContext context = new VelocityContext();

        context.put("controller", this);
        context.put("req", req);

        Template template = null;

        try {
            template = Velocity.getTemplate(page);
        }
        catch (Exception e) {

            throw new ServletException(e);
        }

        template.merge(context, resp.getWriter());
    }

    public static void setMessage(String message) {
        ControllerFilter.message = message;
    }
}
