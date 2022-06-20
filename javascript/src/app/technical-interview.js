const promptSync = require( 'prompt-sync')
const prompt = promptSync({})

const Q = require( '../data/categories.json')

let CAN = { firstName: '' }
const CAT = []

const TECHNICAL_WORKSHOP = {
    candidate : { ...CAN },
    cat: CAT,
    addCat: (label) => {
        console.log(`Adding ${label} in categories`)

        this?.cat?.push(label)
    },
    addCandidate: (firstName, lastName, email) => {
        console.log(`Adding ${firstName} as candidate`)
        this.candidate = {
            firstName,
            lastName,
            email
        }
    },
    getCandidate: () => this?.candidate,
    loadQ: () => {
        return [...Q]
    },
    loadQByCat(cat) {
        const CAT = this?.loadQ().find(q => q.label === cat)
        return CAT?.questions
    },
    run(category) {

        const Q = this?.loadQByCat(category)
        const R = []

        console.log(`Welcome to the interview game. You'll have ${Q?.length} questions on ${category}`)

        const ready = prompt('Are you ready? Press y and Enter to start. ', '',{echo: ''});
        if(ready === 'y'){
            console.log(`\nLet's go!\n`)
            console.log('***************** Questions *****************\n')
            Q?.forEach(quest => {
                const a = prompt(quest.label+' ', '', {})
                R.push({question: quest, answer: a})
            })
            console.log('\nThank you for your participation!\n')
        }
        console.log(`\n***************** Response from: ${this.getCandidate().firstName} *****************\n`)
        let s = 0.0
        R?.forEach(r => {
            const q = r?.question
            console.log(`> Question: ${r.question.label} \n>>> Response: ${r.answer}. \n`)
            const A = prompt('----> What is your evaluation: t=true or f=false ? ', '', {})
            if(A === 't' || A === 'T'){
                switch(q?.difficulty){
                    case 1:
                        s += 0.25
                        break
                    case 2:
                        s += 0.5
                        break
                    case 3:
                        s += 0.75
                        break
                    case 4:
                        s += 1
                        break
                }
            }
        })
        return s;
    },
}

module.exports = TECHNICAL_WORKSHOP
