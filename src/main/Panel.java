package main;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Panel extends JPanel {
	private JTextField rutaArchivoHttpShow, rutaRootShow;
	private String rutaArchivoHttp = "C:\\AppServ\\Apache24\\conf\\httpd.conf", rutaRoot;
	private JFileChooser fc;
	private FileReplace fileReplace;
	/**
	 * Create the panel.
	 */
	public Panel() {
		setLayout(null);
		
		fileReplace = new FileReplace();
		fc = new JFileChooser();
		
		JLabel lblDirectorioHttpdconf = new JLabel("Directorio httpd.conf");
		lblDirectorioHttpdconf.setBounds(42, 51, 221, 14);
		add(lblDirectorioHttpdconf);
		
		JButton btnChangeRoot = new JButton("Elegir Archivo");
		btnChangeRoot.setBounds(52, 69, 130, 23);
		btnChangeRoot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(Panel.this);

	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();
	                rutaArchivoHttp = file.getAbsolutePath();
	                rutaArchivoHttpShow.setText(rutaArchivoHttp);
	            } 
			}
		});
		add(btnChangeRoot);
		
		rutaArchivoHttpShow = new JTextField();
		rutaArchivoHttpShow.setBounds(192, 70, 318, 20);
		add(rutaArchivoHttpShow);
		rutaArchivoHttpShow.setColumns(10);
		rutaArchivoHttpShow.setText(rutaArchivoHttp);
		
		JLabel label = new JLabel("Directorio root (index.php)");
		label.setBounds(42, 126, 221, 14);
		add(label);
		
		JButton button = new JButton("Elegir Archivo");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(Panel.this);

	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();
	                rutaRoot = normalizeUrl(file.getAbsolutePath(),1);
	                rutaRootShow.setText(rutaRoot);
	            } 
			}
		});
		button.setBounds(52, 144, 130, 23);
		add(button);
		
		rutaRootShow = new JTextField();
		rutaRootShow.setColumns(10);
		rutaRootShow.setBounds(192, 145, 318, 20);
		add(rutaRootShow);
		
		JButton btnCambiarRoot = new JButton("Cambiar root");
		btnCambiarRoot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] commandRestart = {"httpd","-k","restart"};
				if(rutaRoot == null) {
					rutaRoot = rutaRootShow.getText();
				}
				fileReplace.replace(rutaArchivoHttp, rutaRoot);
				ExecCMD.ejecutarCMD(commandRestart); //Reiniciamos el servicio
				JOptionPane.showMessageDialog(Panel.this, "Directorio cambiado con éxito", "Completado", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnCambiarRoot.setBounds(209, 217, 151, 23);
		add(btnCambiarRoot);

	}
	
	public String normalizeUrl(String url, int limit) {  
		int indexSplit = url.length()-1;
		int found = 0;
		for(int i = url.length()-1; found!=limit && i >= 0; i--) {
			if(url.charAt(i) == '\\'){
				indexSplit = i;
				found++;
			}
					
		}
		return url.substring(0, indexSplit);
    }  
}


