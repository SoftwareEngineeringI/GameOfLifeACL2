
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUI extends JFrame implements AssetProvider {
  private static final long serialVersionUID = 2468802632497148995L;
  
  private JTextArea inputEditor = new JTextArea(); 
  
  private JPanel bottomBar = new JPanel();
  private JButton setExecutable = new JButton("Set Executable");
  private JButton start = new JButton("Start");
  private JButton stop = new JButton("Stop");
  private JButton random = new JButton ("Random");
  private JTextField xCorInput = new JTextField("---");
  private JTextField yCorInput = new JTextField("---");
  private JButton add = new JButton ("add");
  private JButton clear = new JButton ("Clear");
  
  String[] objects = { "Blinker", "Glider", "Beehive", "Single", "Block"};
  private JComboBox objectList = new JComboBox(objects);

//  NEED to set current Executable
  private String currentExecutable = "";
  private String cachedHtmlOutput = "";
  private String cachedInput = "";
  private String cachedAliveCells = "";
  
  private boolean started = false;
  
  private Grid grid;
  
  private AsyncProgramRunner runner = new AsyncProgramRunner();
  
  public GUI() {
    setTitle("Game of Life"); 	
    boolean badHeightInput = true;
    boolean badWidthInput = true;
    int width = -1;
	int height = -1;
    while (badHeightInput) {
	    try {
	    	String input = JOptionPane.showInputDialog("Enter the height:");
	    	if (input == null) {
	    		System.exit(0);
	    	}
	    	height = Integer.parseInt(input);
	    	badHeightInput = false;
	    }
	    catch (Exception e) {
	    	System.out.println("Height must be integer");
	    }
    }
    while (badWidthInput) {
	    try {
	    	String input = JOptionPane.showInputDialog("Enter the width:");
	    	if (input == null) {
	    		System.exit(0);
	    	}
	    	width = Integer.parseInt(input);
	    	badWidthInput = false;
	    }
	    catch (Exception e) {
	    	System.out.println("Width must be integer");
	    }
    }
    
    System.out.println(height + " " + width);
    grid = new Grid(width, height);
    
    getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    add(inputEditor);
    add(bottomBar);
       
    setExecutable.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        setExecutablePrompt();
      }
    });
    
    start.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        started = true;
        updateState();
        cachedAliveCells = "";
        for (int s : grid.getAliveCells()) {
        	cachedAliveCells += s + " ";
        }
        System.out.println(cachedInput);
        try {
          writeFile("world.txt", cachedAliveCells);
        } catch (final IOException e1) {
          e1.printStackTrace();
        }
      }
    });
    
    stop.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        started = false;
        updateState();
      }
    });
    clear.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
        	grid.clearGrid();
        	try {
	             writeFile("display.htm", "");
	             writeFile("world.txt", "");
	        } catch (final IOException e1) {
	             e1.printStackTrace();
	        }
        	updateState();
        }
      });
    
    random.addActionListener( new ActionListener () {
    	@Override
    	public void actionPerformed (final ActionEvent e) {
    		System.out.println ("Clicked Random");
    		grid.clearGrid();
//    		When we click random, stop the game update the state and randomize the inputs.
    		started = false;
    		Random rand = new Random();

    		for ( int i = 0; i < rand.nextInt( ( grid.getHeight() * grid.getWidth() ) / 2 ) + 1; i++) {
    			int x = rand.nextInt( grid.getWidth() ) + 1;
    			int y = rand.nextInt( grid.getHeight() ) + 1;
    			grid.single(Math.abs(x),Math.abs(y));
    			System.out.println("Added Single at " + x + " " + y);
    		}
    		updateState();
    	}
    });
    
    add.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
        	int xint = -1;
        	int yint = -1;
        	
        	try {
        		xint = Integer.parseInt(xCorInput.getText());
        		yint = Integer.parseInt(yCorInput.getText());
        	}
        	catch (Exception ex) {
        		System.out.println("Coordinates must be integers");
        		return;
        	}
        	try {
	        	if (objectList.getSelectedItem() == "Block") {
	        		grid.block(xint, yint);
	        	} else if (objectList.getSelectedItem() == "Glider") {
	        		grid.glider(xint, yint);
	        	} else if (objectList.getSelectedItem() == "Beehive") {
	        		grid.beehive(xint, yint);
	        	} else if (objectList.getSelectedItem() == "Blinker") {
	        		grid.blinker(xint, yint);
	        	} else if (objectList.getSelectedItem() == "Single") { 
	        		grid.single(xint, yint);
	        	} 
	        	System.out.println("Add" + objectList.getSelectedItem() + " at " + xCorInput.getText() + " " + xCorInput.getText());
        	}
        	catch (IllegalArgumentException exc) {
        		System.err.println("Error: " + exc);
        		return;
        	}
        	updateState();
        	
        }
    });
    
    bottomBar.setLayout(new FlowLayout(FlowLayout.LEFT));
    bottomBar.add(setExecutable);
    bottomBar.add(start);
    bottomBar.add(stop);
    bottomBar.add(random);
    bottomBar.add(clear);
    bottomBar.add(new JSeparator(SwingConstants.VERTICAL));
    bottomBar.add(objectList);
    bottomBar.add(new JLabel("at ["));
    bottomBar.add(xCorInput);
    bottomBar.add(new JLabel(","));
    bottomBar.add(yCorInput);
    bottomBar.add(new JLabel("]"));
    bottomBar.add(add);
    setMinimumSize(new Dimension(640, 480));
    
    updateState();
    
    runner.start();
  }
  
  private void updateState() {
    final boolean goodExecutable = !currentExecutable.isEmpty();
    setExecutable.setEnabled(!started);
  
    //Toggle the start and stop buttons
    start.setEnabled(!started);
    stop.setEnabled(started);
    random.setEnabled(!started);
    add.setEnabled(!started);
    objectList.setEnabled(!started);
    xCorInput.setEnabled(!started);
    yCorInput.setEnabled(!started);
    inputEditor.setEnabled(goodExecutable && !started);
    
    if(!goodExecutable) return;
    try {
      findInputFile();
      inputEditor.setText(readFile(cachedInput));
    } catch (final IOException e) {
      e.printStackTrace();
      inputEditor.setEnabled(false);
    }
  }
  
  private void setExecutablePrompt() {
    final JFileChooser fc = new JFileChooser();
    fc.setFileFilter(new javax.swing.filechooser.FileFilter() {
      @Override
      public boolean accept(final File f) {
        return f.isDirectory() || f.canExecute();
      }

      @Override
      public String getDescription() {
        return "Executables";
      }
    });
    
    if(fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
    final File f = fc.getSelectedFile();
    currentExecutable = f.getAbsolutePath();
    
    runner.setProgramPath(currentExecutable);
    runner.setWorkingDirectory(executableDir());

    cachedHtmlOutput = "";
    cachedInput = "";
    updateState();
  }

  /**
   * This is called by the HTTP server when it needs a refreshed asset.
   * We simply call the executable synchronously.
   */
  @Override
  public boolean updateAsset() {
    if(currentExecutable.isEmpty() || !started) return false;
    
    runner.requestExecute();
    return true;
  }
  
  private File executableDir() {
    final File executableFile = new File(currentExecutable);
    return executableFile.getParentFile();
  }
  
  /**
   * We don't know the name of the html file the program is going to
   * create, but we do know it will probably be in the same working
   * directory. We use the first html file we find in the working
   * directory.
   */
  private void findHtmlOutput() throws IOException {
    if(!cachedHtmlOutput.isEmpty()) return;

    final File[] files = executableDir().listFiles(new FileFilter() {
      @Override
      public boolean accept(final File f) {
        return f.getName().endsWith(".htm") | f.getName().endsWith(".html");
      }
    });
    
    if(files.length == 0) throw new IOException("Couldn't find html file to serve");
    cachedHtmlOutput = files[0].getAbsolutePath();
  }
  
  /**
   * We don't know the name of the input file the program is going to
   * read, but we do know it will probably be in the same working
   * directory. We use the first .txt file we find in the working
   * directory.
   */
  private void findInputFile() throws IOException {
    final File[] files = executableDir().listFiles(new FileFilter() {
      @Override
      public boolean accept(final File f) {
        return f.getName().endsWith(".txt") |
        	   f.getName().startsWith("input");
      }
    });
    if(files.length == 0)
      throw new IOException("Couldn't find input file for reading");
    cachedInput = files[0].getAbsolutePath();
  }
  
  private static String readFile(final String fileName) throws IOException {
    final FileInputStream input = new FileInputStream(fileName);
    final StringBuilder s = new StringBuilder();
    final byte[] buffer = new byte[1024];
    while(input.available() > 0) {
      final int read = input.read(buffer);
      s.append(new String(buffer, 0, read));
    }
    input.close();
    return s.toString();
  }
  
  private static void writeFile(final String fileName, final String contents) throws IOException {
    final FileOutputStream output = new FileOutputStream(fileName);
    output.write(contents.getBytes());
    output.close();
  }

  @Override
  public String retrieveAsset() throws IOException {
    try {
      findHtmlOutput();
    } catch(final IOException e) {
      return "";
    }
    
    inputEditor.setText(readFile(cachedInput));
    
    // Return the entire file's contents
    return readFile(cachedHtmlOutput);
  }
  
}
