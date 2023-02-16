// pipeline {
//   agent any
//
//   environment {
//     IMAGE_NAME = "my-spring-boot-app"
//     CONTAINER_NAME = "my-spring-boot-container"
//   }
//
//   stages {
//     stage('Build') {
//       steps {
//         sh "./mvnw clean package -DskipTests"
//       }
//     }
//
//     stage('Docker Build') {
//       steps {
//         script {
//           docker.build("${IMAGE_NAME}:${BUILD_NUMBER}")
//         }
//       }
//     }
//
//     stage('Docker Run') {
//       steps {
//         script {
//           docker.run("-d -p 8080:8080 --name ${CONTAINER_NAME} --link mysql:mysql ${IMAGE_NAME}:${BUILD_NUMBER}")
//         }
//       }
//     }
//   }
//
//   post {
//     always {
//       sh "docker rm -f ${CONTAINER_NAME}"
//       sh "docker rmi ${IMAGE_NAME}:${BUILD_NUMBER}"
//     }
//   }
// }