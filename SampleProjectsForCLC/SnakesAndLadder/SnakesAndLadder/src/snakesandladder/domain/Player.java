/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snakesandladder.domain;

/**
 *
 * @author trana
 */
public class Player {
    private boolean status;
    private String icon;
    private String name;
    private int position;

    public Player(String icon, String name){
        setPosition(0);
        setStatus(false);
        setIcon(icon);
        setName(name);
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }

}
