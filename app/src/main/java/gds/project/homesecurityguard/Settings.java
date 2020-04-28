package gds.project.homesecurityguard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import gds.project.framework.FileIO;

public class Settings {
	public static boolean soundEnabled = true;
	public final static boolean[] ends = new boolean[]{
		false, false, false, false, false, false
	};
	public final static String file = ".homesecurityguard";

	public static void load(FileIO files) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(files.readFile(file)));
			soundEnabled = Boolean.parseBoolean(in.readLine());
			for(int i = 0; i < 6; i++) {
				ends[i] = Boolean.parseBoolean(in.readLine());
			}
		} catch (IOException e) {

		} catch (NumberFormatException e) {

		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}

	public static void save(FileIO files) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(files.writeFile(file)));
			out.write(Boolean.toString(soundEnabled));
			out.write("\n");
			for(int i = 0; i < 6; i++) {
				out.write(Boolean.toString(ends[i]));
				out.write("\n");
			}

		} catch (IOException e) {
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}

	public static void addScore(int score, int missScore) {
		if(1 < missScore) {
			if(score < 11) {
				ends[0] = true;
			} else if(score < 20) {
				ends[1] = true;
			} else if(score < 40) {
				ends[2] = true;
			} else if(score < 50) {
				ends[3] = true;
			} else {
				ends[4] = true;
			} 
		} else if(missScore == 1) {
			if(score < 20) {
				ends[1] = true;
			} else if(score < 40) {
				ends[2] = true;
			} else if(score < 50) {
				ends[3] = true;
			} else {
				ends[4] = true;
			} 
		} else if(missScore < 1) {
			if(score < 20) {
				ends[1] = true;
			} else if(score < 40) {
				ends[2] = true;
			} else if(score < 50) {
				ends[3] = true;
			} else if(score < 60) {
				ends[4] = true;
			} else {
				ends[5] = true;
			}
		}
	}			
}
