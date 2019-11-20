module.exports = {
    pages: {
        index: {
            entry: 'src/pages/login/login.js',
            template: 'src/pages/login/login.html',
            file: 'login.html'
        },
        console: {
            entry: 'src/pages/console/console.js',
            template: 'src/pages/console/console.html'
        }
    },
    devServer: {
        port: 3030,
        // proxy: {
        //     '/api': {
        //         target: 'http://localhost:3030/'
        //     }
        // }
    }
};