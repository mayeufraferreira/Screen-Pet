package br.edu.ufape.screenpet.business.register;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufape.screenpet.data.InterfaceCollectionTreatment;
import br.edu.ufape.screenpet.business.basic.Diagnosis;
import br.edu.ufape.screenpet.business.basic.Treatment;
import br.edu.ufape.screenpet.business.register.exception.DuplicateTreatmentException;
import br.edu.ufape.screenpet.business.register.exception.DoesNotTreatmentExistsException;

@Service
public class RegisterTreatment {
	@Autowired
	private InterfaceCollectionTreatment collectionTreatment;
	
	public Treatment findTreatment(Diagnosis diagnosis) throws DoesNotTreatmentExistsException {
		Treatment treatment = collectionTreatment.findByDiagnosis(diagnosis); 
		if(treatment == null) {
			throw new DoesNotTreatmentExistsException(diagnosis);
		}
		return treatment;
	}
	
	public Treatment saveTreatment(Treatment entity) throws DoesNotTreatmentExistsException, DuplicateTreatmentException {
		try {
			findTreatment(entity.getDiagnosis());
			throw new DuplicateTreatmentException(entity.getDiagnosis());
		} catch(DoesNotTreatmentExistsException err) {
			return collectionTreatment.save(entity);
		}
	}

	public List<Treatment> listTreatments() {
		return collectionTreatment.findAll();
	}

	public boolean checkExistenceTreatmentId(Long id) {
		return collectionTreatment.existsById(id);
	}

	public Treatment findTreatmenId(Long id) {
		return collectionTreatment.findById(id).orElse(null);
	}
}