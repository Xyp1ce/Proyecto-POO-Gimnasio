package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Pago implements Serializable
{
	private static final long serialVersionUID = 1L;

	// ATRIBUTOS
	private long idPago;
	private long idCliente;
	private float monto;
	private LocalDate fechaPago;
	private String metodoPago; // "Efectivo", "Tarjeta", "Transferencia"
	private String concepto;
	private String referencia;

	// CONSTRUCTORES
	public Pago()
	{
		this.idPago = generarIdPago();
		this.fechaPago = LocalDate.now();
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
		this.fechaPago = LocalDate.now();
		this.metodoPago = metodoPago;
		this.concepto = "Pago de membresía";
		this.referencia = generarReferencia();
	}

	public Pago(long idCliente, float monto, String metodoPago, String concepto)
	{
		this.idPago = generarIdPago();
		this.idCliente = idCliente;
		this.monto = monto;
		this.fechaPago = LocalDate.now();
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

	public LocalDate obtenerFechaPago()
	{
		return fechaPago;
	}

	public void definirFechaPago(LocalDate fechaPago)
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
				fechaPago != null;
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
		LocalDate limite = LocalDate.now().minusDays(dias);
		return fechaPago.isAfter(limite) || fechaPago.isEqual(limite);
	}

	// METODOS PRIVADOS DE SOPORTE

	private long generarIdPago()
	{
		return (long) (Math.random() * 9000000L) + 1000000L;
	}

	private String generarReferencia()
	{
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd");
		String fecha = LocalDate.now().format(formato);
		int aleatorio = (int) (Math.random() * 9000) + 1000;
		return "PAG-" + fecha + "-" + aleatorio;
	}

	// REPRESENTACION EN TEXTO

	@Override
	public String toString()
	{
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return  "Pago{" +
				"id=" + idPago +
				", cliente=" + idCliente +
				", monto=$" + String.format("%.2f", monto) +
				", fecha=" + fechaPago.format(formato) +
				", metodo='" + metodoPago + '\'' +
				", concepto='" + concepto + '\'' +
				", referencia='" + referencia + '\'' +
				'}';
	}

	public String generarRecibo()
	{
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		return  "\n========== RECIBO DE PAGO ==========\n" +
				"Referencia: " + referencia + "\n" +
				"Fecha: " + fechaPago.format(formato) + "\n" +
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