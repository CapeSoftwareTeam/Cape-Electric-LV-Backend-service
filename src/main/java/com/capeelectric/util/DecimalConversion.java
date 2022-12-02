package com.capeelectric.util;

import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capeelectric.exception.DecimalConversionException;

/**
 * This DecimalConversion Util class doing StringInt value convert to
 * StringDecimal
 * 
 * @author capeelectricsoftware
 *
 */
public class DecimalConversion {

	private static final Logger logger = LoggerFactory.getLogger(DecimalConversion.class);

	private static String decimalValues = "";

	/**
	 * @param String value,decimalSize convertToDecimal method to StringInt value
	 *               convert to StringDecimal
	 * @return string
	 * @throws DecimalConversionException 
	 */
	public static String convertToDecimal(String value, String type) throws DecimalConversionException {
		try {
			DecimalConversion conversion = new DecimalConversion();
			String nominalValues = "";
			DecimalFormat decimalSize;
			decimalSize = conversion.findDecimalSize(type);
			// for multiple value
			String decimalValue = "NA";
			if (value != null && !value.isEmpty()) {
				
				StringTokenizer stringTokenizer = new StringTokenizer(value, ",");

				logger.info("started DecimalConversion process");
				while (stringTokenizer.hasMoreElements()) {
					String token = stringTokenizer.nextToken();
					// String contain "NA"  
					token = isStartWithDot(token);
					if (token.equalsIgnoreCase("NA") || !Pattern.compile("-?\\d+(\\.\\d+)?").matcher(token).matches()) {
						nominalValues = nominalValues.concat(token).concat(",");
					} else {
						if (isDecimalNum(token, decimalSize)) {
							nominalValues = nominalValues.concat(decimalValues).concat(",");
						} else {
							decimalValue = decimalSize.format(Double.parseDouble(token));
							nominalValues = nominalValues.concat(decimalValue).concat(",");
						}
					}

				}
			} else {
				logger.info("failed DecimalConversion process");
				throw new DecimalConversionException("invalid input of value for DecimalConversion");
			}
			logger.info("ended DecimalConversion process");
			return nominalValues.substring(0, nominalValues.length() - 1);

		} catch (DecimalConversionException e) {
			logger.error(type);
			throw new DecimalConversionException(e.getMessage());

		} catch (Exception e) {
			logger.error(e.getMessage() + "" + type);
			throw new DecimalConversionException(type);

		}
	}

	private static String isStartWithDot(String value) {
		if (!value.isEmpty() && value.charAt(0) == '.') {
			return "0" + value;
		} else {
			return value;
		}
	}

	private DecimalFormat findDecimalSize(String type) throws DecimalConversionException {
		if (type != null) {
			if (type.equalsIgnoreCase(Constants.supply_MainNominal_Current)) {
				return new DecimalFormat(Constants.supplyMainNominalCurrent);
			} else if (type != null && type.equalsIgnoreCase(Constants.supply_MainNominal_Frequency)) {
				return new DecimalFormat(Constants.supplyMainNominalFrequency);
			} else if (type != null && type.equalsIgnoreCase(Constants.supply_MainNominal_Voltage)) {
				return new DecimalFormat(Constants.supplyMainNominalVoltage);
			} else if (type != null && type.equalsIgnoreCase(Constants.supply_MainLoop_Impedance)) {
				return new DecimalFormat(Constants.supplyMainLoopImpedance);
			} else if (type != null && type.equalsIgnoreCase(Constants.supply_Nominal_Frequency)) {
				return new DecimalFormat(Constants.supplyNominalFrequency);
			} else if (type != null && type.equalsIgnoreCase(Constants.supply_Nominal_Voltage)) {
				return new DecimalFormat(Constants.supplyNominalVoltage);
			} else if (type != null && type.equalsIgnoreCase(Constants.supply_Fault_Current)) {
				return new DecimalFormat(Constants.supplyFaultCurrent);
			} else if (type != null && type.equalsIgnoreCase(Constants.supply_LoopImpedance)) {
				return new DecimalFormat(Constants.supplyLoopImpedance);
			} else if (type != null && type.equalsIgnoreCase(Constants.supply_Earth_Resistance)) {
				return new DecimalFormat(Constants.supplyEarthResistance);
			} else if (type != null && type.equalsIgnoreCase(Constants.supply_Grid_Resistance)) {
				return new DecimalFormat(Constants.supplyGridResistance);
			} else if (type != null && type.equalsIgnoreCase(Constants.supply_Earth_Depth)) {
				return new DecimalFormat(Constants.supplyEarthDepth);
			} else if (type != null && type.equalsIgnoreCase(Constants.supply_Earth_Electrode_Size)) {
				return new DecimalFormat(Constants.supplyEarthElectrodeSize);
			} else if (type != null && type.equalsIgnoreCase(Constants.supply_Earth_JointResistance)) {
				return new DecimalFormat(Constants.supplyEarthJointResistance);
			} else if (type != null && type.equalsIgnoreCase(Constants.supply_Bounding_JointResistance)) {
				return new DecimalFormat(Constants.supplyBoundingJointResistance);
			} else if (type != null && type.equalsIgnoreCase(Constants.supply_ResidualTime)) {
				return new DecimalFormat(Constants.supplyResidualTime);
			}

			// Testing
			else if (type != null && type.equalsIgnoreCase(Constants.test_Incoming_LoopImpedance)) {
				return new DecimalFormat(Constants.testIncomingLoopimpedance);
			} else if (type != null && type.equalsIgnoreCase(Constants.test_Short_CircuitSetting)) {
				return new DecimalFormat(Constants.testShortCircuit);
			} else if (type != null && type.equalsIgnoreCase(Constants.test_EFSetting)) {
				return new DecimalFormat(Constants.testInstantaneousDmt);
			} else if (type != null && type.equalsIgnoreCase(Constants.test_Insulation_Resistance)) {
				return new DecimalFormat(Constants.testInsulationResistance);
			} else if (type.equalsIgnoreCase(Constants.test_Voltage)) {
				return new DecimalFormat(Constants.testVoltage);
			} else if (type != null && type.equalsIgnoreCase(Constants.test_Loopimpedance)) {
				return new DecimalFormat(Constants.loopImpedance);
			} else if (type != null && type.equalsIgnoreCase(Constants.test_Faultcurrent)) {
				return new DecimalFormat(Constants.faultCurrent);
			} else if (type != null && type.equalsIgnoreCase(Constants.test_DisConnection)) {
				return new DecimalFormat(Constants.testDisConnection);
			} else if (type != null && type.equalsIgnoreCase(Constants.conductor_Phase)) {
				return new DecimalFormat(Constants.conductorPhase);
			} else if (type != null && type.equalsIgnoreCase(Constants.conductor_Neutral)) {
				return new DecimalFormat(Constants.conductorNeutral);
			} else if (type != null && type.equalsIgnoreCase(Constants.conductor_Pecpc)) {
				return new DecimalFormat(Constants.conductorPecpc);
			} else if (type != null && type.equalsIgnoreCase(Constants.continutiy_Approximate_Length)) {
				return new DecimalFormat(Constants.continutiyApproximateLength);
			} else if (type != null && type.equalsIgnoreCase(Constants.continutiy_RR)) {
				return new DecimalFormat(Constants.continutiyRR);
			} else if (type != null && type.equalsIgnoreCase(Constants.continutiy_R)) {
				return new DecimalFormat(Constants.continutiyR);
			} else if (type != null && type.equalsIgnoreCase(Constants.maxAllowed_Value)) {
				return new DecimalFormat(Constants.maxAllowedValue);
			}
		} else {
			throw new DecimalConversionException("Finding DecimalConversion variable type failed");
		}
		return null;
	}

	private static boolean isDecimalNum(String token,DecimalFormat decimalSize) throws DecimalConversionException {
		boolean flag = false;
		if (Pattern.matches("([0-9]*)\\.([0-9]*)", token)) {
			if (token.split("\\.").length<=2) {
				if (token.split("\\.")[1].length() == decimalSize.getMaximumFractionDigits()) {
					decimalValues = token;
					flag = true;
				} else if (token.split("\\.")[1].length() > decimalSize.getMaximumFractionDigits()) {
					decimalValues = String.format("%." + decimalSize.getMaximumFractionDigits() + "f",
							Double.parseDouble(token));
					flag = true;
				} else if (token.split("\\.")[1].length() < decimalSize.getMaximumFractionDigits()) {
					flag = false;
				}
			}else {
				logger.debug("Given number has multiple points"+token);
				throw new DecimalConversionException("Given number has multiple points");
			}
		}
		return flag;
	}
	
//	public static String oneDigitConvertion(String decimalString) throws DecimalConversionException {
//
//		DecimalFormat decimalSize=new DecimalFormat("0.0");
//
//		if (isDecimalNum(decimalString, decimalSize)) {
//			if (!decimalString.matches("[0-9]+")) {
//				return decimalValues;
//			} else {
//				return decimalSize.format(Double.parseDouble(decimalString));
//			}
//		} else {
//			return decimalString;
//		}
//
//	}

//	public static String threeDigitConvertion(String decimalString) throws DecimalConversionException {
//		DecimalFormat decimalSize=new DecimalFormat("0.000");
//		if (isDecimalNum(decimalString, decimalSize)) {
//			if (!decimalString.matches("[0-9]+")) {
//				return decimalValues;
//			} else {
//				return decimalSize.format(Double.parseDouble(decimalString));
//			}
//		} else {
//			return decimalString;
//		}
//	}
}
