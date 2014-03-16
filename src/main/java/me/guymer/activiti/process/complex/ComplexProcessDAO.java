package me.guymer.activiti.process.complex;

import me.guymer.activiti.AbstractRepository;

import org.springframework.stereotype.Repository;

@Repository
public class ComplexProcessDAO extends AbstractRepository<ComplexProcess> {

	public ComplexProcessDAO() {
		super(ComplexProcess.class);
	}
}
