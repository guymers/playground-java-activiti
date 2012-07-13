package me.guymer.activiti.process.complex;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "complex_process")
public class ComplexProcess implements Serializable {
	
	private static final long serialVersionUID = 7550691175464848498L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "complex_process_id")
	private int id;
	
	@Column(name = "information", length = 100)
	private String information;
	
	@Column(name = "amount")
	private int amount;
	
}
