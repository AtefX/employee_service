node {


        stage('Build') {
    
                sh 'mvn clean package'
    
        }

        stage('Test') {
  
                sh 'mvn test'
         
        }

        stage('Deploy') {
   
                sh 'java -jar target/employee-service.jar'
            

    }
}
