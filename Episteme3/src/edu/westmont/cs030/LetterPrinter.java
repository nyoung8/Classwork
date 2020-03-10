/**
 * Nathan Young
 * Episteme Ch3
 * LetterPrinter.java
 * 2/4/19
 */
package edu.westmont.cs030;

/**
 * A class that prints out an example letter using the Letter class
 */
public class LetterPrinter {

	/**
	 * Using the available methods of the Letter() object, a message 
	 * is created and printed to the console for Mary to send to John.
	 */
	public static void main(String[] args) {

		Letter goodbye = new Letter("Mary", "John");
		
		/*
		goodbye.addLine("RCFYP");
		goodbye.addLine("CZGATUM");
		goodbye.addLine("WHLNMSONL");
		goodbye.addLine("AHRCIIOSLEZ");
		goodbye.addLine("OWIHLLRRSZR");
		goodbye.addLine("LOSOOYFUMEO");
		goodbye.addLine("ONTCRVALTRG");
		goodbye.addLine("SMOALINNN");
		goodbye.addLine("ALCGIAI");
		goodbye.addLine("TPSAHWMTKXI");
		goodbye.addLine("RNETTEEAFKVZE");
		goodbye.addLine("EPANSENKILSTVPQ");
		goodbye.addLine("EUGARGTSRREEDNIER");
		goodbye.addLine("ATSHAXBEYTIVITANF");
		goodbye.addLine("QQOOTWPNEEARMUFFS");
		goodbye.addLine("PMWVSLEJSLEDDINGK");
		goodbye.addLine("EIJTAZGXPRESENTSB");
		goodbye.addLine("VLVCONMSEIKOOCKUO");
		goodbye.addLine("KERHOTCHOCOLATE");
		goodbye.addLine("FHAKKUNAHODUZ");
		*/
		goodbye.addLine("FE    ");
		goodbye.addLine("WK  ");
		goodbye.addLine("YB");
		goodbye.addLine("SPSB IASF  NUP");
		goodbye.addLine("ODBTCSFOMAIZEQZF");
		goodbye.addLine("XACPSTAYODUPDGOAWA");
		goodbye.addLine("DLMOUETRNOZGAKLFTD");
		goodbye.addLine("PYVFNOTYJEVTTSLDSFUL");
		goodbye.addLine("IMPBJKKPAZCSBUTEPCNA");
		goodbye.addLine("AYAHIBXIGASRDAVSIYPE");
		goodbye.addLine("HTNYTLQDEMKEORLDTPEI");
		goodbye.addLine("CUMLEAVESSMPAWELLWXP");
		goodbye.addLine("QRUKHDUBDYRHTRPEFWVG");
		goodbye.addLine("XKTNKLTHANKSGIVINGIA");
		goodbye.addLine("EUERSUNFLOWERMLVYF");
		goodbye.addLine("YAKAOEUYRNNEBSWFEC");
		goodbye.addLine("NROCCYDWPUMPKINJ");
		goodbye.addLine("ZOGFARCINNAMON");
		goodbye.addLine("BMMFEAVLUQIE");
		goodbye.addLine("YHIZC");
		System.out.println(goodbye.getText());
	}

}
