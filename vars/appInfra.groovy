def call() {
    properties([
        parameters([
            choice(name: 'ENV', choices: ['dev', 'prod'], description: 'Environment'),
            choice(name: 'ACTION', choices: ['apply', 'destroy'], description: 'Action')                            
        ]),
    ])
    node {
        ansiColor('xterm') {
            git branch: 'main', url: "https://github.com/b57-clouddevops/${COMPONENT}.git"
            stage('Terraform Init') {
                sh ''' 
                    cd mutable-infra
                    terrafile -f env-${ENV}/Terrafile
                    terraform init --backend-config=env-${ENV}/${ENV}-backend.tfvars
                ''' 
            }
            stage('Terraform Plan') {
                sh ''' 
                    cd mutable-infra
                    terraform plan  --var-file=env-${ENV}/${ENV}.tfvars
                ''' 
            }
            stage('Terraform Action') {
                sh ''' 
                    cd mutable-infra
                    terraform ${ACTION} -auto-approve --var-file=env-${ENV}/${ENV}.tfvars
                ''' 
            }
        }
    }
}

// def call() {
//     pipeline {
//         agent any 
//         parameters {
//             choice(name: 'ENV', choices: ['dev', 'prod'], description: 'Select the environment')
//         }
//         stages {
//             stage('Terraform Init') {
//                 steps {
//                     sh "cd mutable-infra/"
//                     sh "terrafile -f env-${ENV}/Terrafile"
//                     sh "terraform init --backend-config=env-${ENV}/${ENV}-backend.tfvars"
//                 }
//             }
//             stage('Terraform Plan') {
//                 steps {
//                     sh "terraform plan --var-file env-${ENV}/${ENV}-backend.tfvars"
//                 }
//             }
//             stage('Terraform Action') {
//                 steps {
//                     sh "terraform apply -auto-approva --var-fileenv-${ENV}/${ENV}-backend.tfvars"
//                 }
//             }
//         }
//     }
// }