package entidades;

import java.io.Serializable;

public class Pago implements Serializable
{
	private static final long serialVersionUID = 1L;

	// ATRIBUTOS
	private long idPago;
	private long idCliente;
	private float monto;
	private String fechaPago;	// Formato: "dd/MM/yyyy"
	private String metodoPago;	// "Efectivo", "Tarjeta", "Transferencia"
	private String concepto;
	private String referencia;

	// CONSTRUCTORES
	public Pago()
	{
		this.idPago = generarIdPago();
		this.fechaPago = obtenerFechaActual();
		this.metodoPago = "Efectivo";
		this.concepto = "Pago de membresía";
		this.monto = 0.0f;
		this.referencia = "";
	}

	public Pago(long idCliente, float monto, String metodoPago)
	{
		this.idPago = generarIdPago();
		this.idCliente = idCliente;
		this.monto = monto;
		this.fechaPago = obtenerFechaActual();
		this.metodoPago = metodoPago;
		this.concepto = "Pago de membresía";
		this.referencia = generarReferencia();
	}

	public Pago(long idCliente, float monto, String metodoPago, String concepto)
	{
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
		return monto > 0;
	}

	public boolean validarMetodoPago()
	{
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
		return validarMonto() &&
				validarMetodoPago() &&
				idCliente > 0 &&
				fechaPago != null && !fechaPago.isEmpty();
	}

	// METODOS DE NEGOCIO

	public float calcularIVA()
	{
		return monto * 0.16f;
	}

	public float obtenerMontoConIVA()
	{
		return monto + calcularIVA();
	}

	public boolean esReciente(int dias)
	{
		String fechaActual = obtenerFechaActual();
		int diasTranscurridos = calcularDiasEntre(fechaPago, fechaActual);
		return diasTranscurridos <= dias;
	}

	// METODOS PRIVADOS DE SOPORTE

	private long generarIdPago()
	{
		return (long) (Math.random() * 9000000L) + 1000000L;
	}

	private String obtenerFechaActual()
	{
		java.util.Calendar cal = java.util.Calendar.getInstance();
		int dia = cal.get(java.util.Calendar.DAY_OF_MONTH);
		int mes = cal.get(java.util.Calendar.MONTH) + 1; // Enero es 0
		int year = cal.get(java.util.Calendar.YEAR);
		return String.format("%02d/%02d/%04d", dia, mes, year);
	}

	private String generarReferencia()
	{
		java.util.Calendar cal = java.util.Calendar.getInstance();
		int dia = cal.get(java.util.Calendar.DAY_OF_MONTH);
		int mes = cal.get(java.util.Calendar.MONTH) + 1;
		int year = cal.get(java.util.Calendar.YEAR);

		String fecha = String.format("%04d%02d%02d", year, mes, dia);
		int aleatorio = (int) (Math.random() * 9000) + 1000;
		return "PAG-" + fecha + "-" + aleatorio;
	}

	private int calcularDiasEntre(String fecha1, String fecha2)
	{
		String[] partes1 = fecha1.split("/");
		String[] partes2 = fecha2.split("/");

		if (partes1.length != 3 || partes2.length != 3)
			return 0;

		int dia1 = Integer.parseInt(partes1[0]);
		int mes1 = Integer.parseInt(partes1[1]);
		int year1 = Integer.parseInt(partes1[2]);

		int dia2 = Integer.parseInt(partes2[0]);
		int mes2 = Integer.parseInt(partes2[1]);
		int year2 = Integer.parseInt(partes2[2]);

		// Cálculo aproximado
		int diasYears = (year2 - year1) * 365;
		int diasMeses = (mes2 - mes1) * 30;
		int diasDias = (dia2 - dia1);

		return Math.abs(diasYears + diasMeses + diasDias);
	}

	// REPRESENTACION EN TEXTO

	@Override
	public String toString()
	{
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
}