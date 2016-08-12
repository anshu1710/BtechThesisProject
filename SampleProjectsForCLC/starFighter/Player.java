import java.util.*;
import java.io.*;
public class Player
{
	private String name;

	public Player(String n)
	{
		setName(n);
	}

	public void setName(String n)
	{
		name = n;
	}

	public String getName()
	{
		return name;
	}

	public String toString()
	{
		return "Name: " + getName();
	}
}