package entidades;

import java.io.Serializable;

public class Pago implements Serializable, ConCosto, Reportable
{
	private static final long serialVersionUID = 1L;

	// ATRIBUTOS
	private long idPago;
	private long idCliente;
	private float monto;
	private String fechaPago; // FORMATO DD/MM/YYYY
	private String metodoPago; // POSIBLES VALORES EFECTIVO TARJETA TRANSFERENCIA
	private String concepto;
	private String referencia;

	// CONSTRUCTORES
	public Pago()
	{
		// PREPARAR OBJETO CON VALORES POR DEFECTO PARA INICIALIZACIONES AUTOMATICAS
		this.idPago = generarIdPago();
		this.fechaPago = obtenerFechaActual();
		this.metodoPago = "Efectivo";
		this.concepto = "Pago de membresia";
		this.monto = 0.0f;
		this.referencia = "";
	}

	public Pago(long idCliente, float monto, String metodoPago)
	{
		// CONSTRUYE UN PAGO TIPICO CON CLIENTE MONTO Y METODO DEFINIDOS
		this.idPago = generarIdPago();
		this.idCliente = idCliente;
		this.monto = monto;
		this.fechaPago = obtenerFechaActual();
		this.metodoPago = metodoPago;
		this.concepto = "Pago de membresia";
		this.referencia = generarReferencia();
	}

	public Pago(long idCliente, float monto, String metodoPago, String concepto)
	{
		// PERMITE PERSONALIZAR EL CONCEPTO SIN DUPLICAR LA LOGICA BASE
		this.idPago = generarIdPago();
		this.idCliente = idCliente;
		this.monto = monto;
		this.fechaPago = obtenerFechaActual();
		this.metodoPago = metodoPago;
		this.concepto = concepto;
		this.referencia = generarReferencia();
	}

	// GETTERS Y SETTERS
	public long obtenerIdPago()
	{
		return idPago;
	}

	public void definirIdPago(long idPago)
	{
		this.idPago = idPago;
	}

	public long obtenerIdCliente()
	{
		return idCliente;
	}

	public void definirIdCliente(long idCliente)
	{
		this.idCliente = idCliente;
	}

	public float obtenerMonto()
	{
		return monto;
	}

	public void definirMonto(float monto)
	{
		this.monto = monto;
	}

	public String obtenerFechaPago()
	{
		return fechaPago;
	}

	public void definirFechaPago(String fechaPago)
	{
		this.fechaPago = fechaPago;
	}

	public String obtenerMetodoPago()
	{
		return metodoPago;
	}

	public void definirMetodoPago(String metodoPago)
	{
		this.metodoPago = metodoPago;
	}

	public String obtenerConcepto()
	{
		return concepto;
	}

	public void definirConcepto(String concepto)
	{
		this.concepto = concepto;
	}

	public String obtenerReferencia()
	{
		return referencia;
	}

	public void definirReferencia(String referencia)
	{
		this.referencia = referencia;
	}

	// METODOS DE VALIDACION

	public boolean validarMonto()
	{
		// IMPIDE MONTOS NEGATIVOS O CERO QUE PROVOCARIAN REPORTES INCORRECTOS
		return monto > 0;
	}

	public boolean validarMetodoPago()
	{
		// ACEPTA SOLO LOS METODOS SOPORTADOS PARA EVITAR INCONSISTENCIAS
		if (metodoPago == null || metodoPago.trim().isEmpty())
			return false;

		String metodo = metodoPago.toLowerCase();
		return metodo.equals("efectivo") ||
				metodo.equals("tarjeta") ||
				metodo.equals("transferencia") ||
				metodo.equals("debito") ||
				metodo.equals("credito");
	}

	public boolean validarPago()
	{
		// AGRUPA TODAS LAS VALIDACIONES PARA UN USO MAS SENCILLO EN CAPAS SUPERIORES
		return validarMonto() &&
				validarMetodoPago() &&
				idCliente > 0 &&
				fechaPago != null && !fechaPago.isEmpty();
	}

	// METODOS DE NEGOCIO

	public float calcularIVA()
	{
		// CALCULA IMPUESTO GENERAL PARA MOSTRARLO POR SEPARADO EN RECIBOS
		return monto * 0.16f;
	}

	public float obtenerMontoConIVA()
	{
		// SUMA SUBTOTAL MAS IVA PARA REFLEJAR MONTO FINAL COBRADO
		return monto + calcularIVA();
	}

	public boolean esReciente(int dias)
	{
		// VERIFICA SI EL PAGO SE ENCUENTRA DENTRO DE UN UMBRAL DE DIAS
		String fechaActual = obtenerFechaActual();
		int diasTranscurridos = calcularDiasEntre(fechaPago, fechaActual);
		return diasTranscurridos <= dias;
	}

	// METODOS PRIVADOS DE SOPORTE

	private long generarIdPago()
	{
		// UTILIZA NUMEROS ALEATORIOS EN UN RANGO PARA REDUCIR COLISIONES EN MEMORIA
		return (long) (Math.random() * 9000000L) + 1000000L;
	}

	private String obtenerFechaActual()
	{
		// ENTREGA FECHA EN FORMATO CONSISTENTE CON LOS ARCHIVOS DE TEXTO
		java.util.Calendar cal = java.util.Calendar.getInstance();
		int dia = cal.get(java.util.Calendar.DAY_OF_MONTH);
		int mes = cal.get(java.util.Calendar.MONTH) + 1; // ENERO ES 0
		int anio = cal.get(java.util.Calendar.YEAR);
		return String.format("%02d/%02d/%04d", dia, mes, anio);
	}

	private String generarReferencia()
	{
		// COMBINA FECHA Y NUMERO ALEATORIO PARA TENER REFERENCIAS UNICAS
		java.util.Calendar cal = java.util.Calendar.getInstance();
		int dia = cal.get(java.util.Calendar.DAY_OF_MONTH);
		int mes = cal.get(java.util.Calendar.MONTH) + 1;
		int anio = cal.get(java.util.Calendar.YEAR);

		String fecha = String.format("%04d%02d%02d", anio, mes, dia);
		int aleatorio = (int) (Math.random() * 9000) + 1000;
		return "PAG-" + fecha + "-" + aleatorio;
	}

	private int calcularDiasEntre(String fecha1, String fecha2)
	{
		// APLICA CALCULO SIMPLE SIN DEPENDER DE LIBRERIAS EXTERNAS
		String[] partes1 = fecha1.split("/");
		String[] partes2 = fecha2.split("/");

		if (partes1.length != 3 || partes2.length != 3)
			return 0;

		int dia1 = Integer.parseInt(partes1[0]);
		int mes1 = Integer.parseInt(partes1[1]);
		int anio1 = Integer.parseInt(partes1[2]);

		int dia2 = Integer.parseInt(partes2[0]);
		int mes2 = Integer.parseInt(partes2[1]);
		int anio2 = Integer.parseInt(partes2[2]);

		// CALCULO APROXIMADO BAJO SUPUESTO DE 30 DIAS POR MES
		int diasAnios = (anio2 - anio1) * 365;
		int diasMeses = (mes2 - mes1) * 30;
		int diasDias = (dia2 - dia1);

		return Math.abs(diasAnios + diasMeses + diasDias);
	}

	// REPRESENTACION EN TEXTO

	@Override
	public String toString()
	{
		// PROPORCIONA RESUMEN COMPACTO PARA LOGS Y DEPURACION
		return "Pago{" +
				"id=" + idPago +
				", cliente=" + idCliente +
				", monto=$" + String.format("%.2f", monto) +
				", fecha=" + fechaPago +
				", metodo='" + metodoPago + '\'' +
				", concepto='" + concepto + '\'' +
				", referencia='" + referencia + '\'' +
				'}';
	}

	public String generarRecibo()
	{
		// CONSTRUYE RECIBO EN TEXTO PLANO PARA IMPRESION O ENVIO DIGITAL
		return "\n========== RECIBO DE PAGO ==========\n" +
				"Referencia: " + referencia + "\n" +
				"Fecha: " + fechaPago + "\n" +
				"Cliente ID: " + idCliente + "\n" +
				"Concepto: " + concepto + "\n" +
				"-----------------------------------\n" +
				"Subtotal: $" + String.format("%.2f", monto) + "\n" +
				"IVA (16%): $" + String.format("%.2f", calcularIVA()) + "\n" +
				"Total: $" + String.format("%.2f", obtenerMontoConIVA()) + "\n" +
				"-----------------------------------\n" +
				"Método de pago: " + metodoPago + "\n" +
				"====================================\n";
	}

	// IMPLEMENTACION DE INTERFACES

	@Override
	public float obtenerCosto()
	{
		// PERMITE UTILIZAR EL PAGO EN CALCULOS GENERICOS DE COSTO
		return monto;
	}

	@Override
	public String generarReporte()
	{
		// COMPILA INFORMACION COMPLETA PARA REPORTES Y EXPORTES
		return "=== REPORTE DE PAGO ===\n" +
				"ID Pago: " + idPago + "\n" +
				"Cliente: " + idCliente + "\n" +
				"Fecha: " + fechaPago + "\n" +
				"Monto: $" + String.format("%.2f", monto) + "\n" +
				"IVA: $" + String.format("%.2f", calcularIVA()) + "\n" +
				"Total: $" + String.format("%.2f", obtenerMontoConIVA()) + "\n" +
				"Método: " + metodoPago + "\n" +
				"Concepto: " + concepto + "\n" +
				"Referencia: " + referencia + "\n" +
				"========================\n";
	}
}