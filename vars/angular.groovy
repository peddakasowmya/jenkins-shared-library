def call() {
    node {
        git branch: 'main', url: "https://github.com/b57-clouddevops/${COMPONENT}.git"
        common.lintchecks()
        env.ARGS="-Dsonar.sources=."
        common.sonarchecks()
        common.testcases()
        if(env.TAG_NAME != null) {
            common.artifacts()
        }
    }
}

// def lintchecks() {
//     sh '''
//         echo Installing Lint Checker
//         npm i jslint
//         echo Performing Lint Checks for $COMPONENT
//         node_modules/jslint/bin/jslint.js server.js || true
//     ''' 
// }

// def call(COMPONENT) {
//     pipeline { 
//         agent any
//         environment {
//             NEXUS_URL = "172.31.38.109"
//             SONAR_CRED  = credentials('SONAR_CRED')
//         }
//         stages {
//             stage('Lint Checks') {
//                 steps {
//                     script {
//                         lintchecks()
//                     }
//                 }
//             }

//             stage('Static Code Analysis') {
//                 steps {
//                     script {
//                         env.ARGS=" -Dsonar.sources=."
//                         common.sonarchecks()
//                     }
//                 }
//             }
  
//             stage('Get Sonar Result') {
//                 steps {
//                     script {
//                         common.sonarresult()
//                     }
//                 }
//             }

//             stage("Testing") {
//                 steps {
//                     script {
//                         common.testcases()
//                     }
//                 }
//             }
            
//             // Should Only Run Against A Tag
//             stage("Making Artifact") {
//             when { 
//                 expression { env.TAG_NAME != null  } 
//             }
//                 steps {
//                     sh ''' 
//                         npm install 
//                         zip -r ${COMPONENT}-${TAG_NAME}.zip node_modules server.js 
//                     '''
//                 }
//             }

//             // Should Only Run Against A Tag
//             stage("Publishing Artifact") {
//             when { 
//                 expression { env.TAG_NAME != null  } 
//             }
//                 steps {
//                     sh '''
//                         echo Publishing Artifacts
//                         curl -f -v -u admin:password --upload-file ${COMPONENT}-${TAG_NAME}.zip http://${NEXUS_URL}:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip
//                     '''

//                 }
//             }
//         }
//     }
// }