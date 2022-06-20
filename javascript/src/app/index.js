const TECHNICAL_WORKSHOP = require('./technical-interview.js')

function runTechnicalInterview(){
    TECHNICAL_WORKSHOP.addCat('SQL')
    TECHNICAL_WORKSHOP.addCandidate('Toto', 'Titi', 'titi@mail.fr')

    const SCORE = TECHNICAL_WORKSHOP.run('Java')
    console.log(`The candidate as a total of ${SCORE} points.`)
}

runTechnicalInterview();
process.exit()
