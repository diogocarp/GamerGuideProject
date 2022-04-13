package br.senai.sp.cfp138.gamerguide.util;


import java.io.IOException;
import java.util.UUID;

import org.hibernate.query.criteria.internal.expression.function.SubstringFunction;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class FireBaseUtil {

	//variavel para guardar as credenciais da firebase
	
	private Credentials credenciais;
	
	//varoavel para acessar o storage
	
	private Storage storage;
	
	// constante para o nome do bucket
	
	private final String BUCKET_NAME = "gamerguide-5d8d9.appspot.com";
	
	// constante para o prefixo da URL 
	
	private String PREFIX = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET_NAME + "/o/";
	
	// constante para o sulfixo da URL
	
	private String SUFFIX = "?alt=media";
	
	// constante para a URL
	
	private final String DOWNLOAD_URL = PREFIX + "%s" + SUFFIX;
	
	public FireBaseUtil() {
		// buscar as credenciais (arquivo JSON)
		
		Resource resource = new ClassPathResource("chavefirebase.json");
		
		// ler o arquivo para obter as credenciais
		
		try {
			credenciais = GoogleCredentials.fromStream(resource.getInputStream());
			
			// acessa o serviço de storage
		
			storage = StorageOptions.newBuilder().setCredentials(credenciais).build().getService();
			
		} catch (IOException e) {
			
			throw new RuntimeException(e.getMessage());
			
		}
	}
	
	public String uploadFile(MultipartFile arquivo) throws IOException {
		// gera uma String aleatoria para o nome do arquivo 
		String nomeArquivo = UUID.randomUUID().toString() +
				getExtensao(arquivo.getOriginalFilename());
		
		// criar um BlobId
		BlobId blobId = BlobId.of(BUCKET_NAME, nomeArquivo);
		
		// criar um BlobInfo a partir do BlobId
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
		
		// manda o blobInfo para Storage passando os bytes do arquivo
		storage.create(blobInfo, arquivo.getBytes());
		
		// retornar a URL para acessar o arquivo
		return String.format(DOWNLOAD_URL, nomeArquivo);
		
	}
	
	// retorna uma extensao de uma arquivo atraves do seu nome
	
	private String getExtensao(String nomeArquivo) {
		
		//retorna o trecho da String que vai do ultimo ponto ate o fim
		
		return nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
		
	}
	
	// metodo para excluir a foto do Firebase
	
	public void deletar(String nomeArquivo) {
		
		// retira o prefixo e sufixo do nome arquivo
		
		nomeArquivo = nomeArquivo.replace(PREFIX, "").replace(SUFFIX, "");
		
		// pega um blob através do nome do arquivo
		
		Blob blob = storage.get(BlobId.of(BUCKET_NAME, nomeArquivo));
		
		// deleta o arquivo
		storage.delete(blob.getBlobId());
		
	}
	
	
	
	
}
