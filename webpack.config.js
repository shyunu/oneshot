var path = require('path');
const webpack = require('webpack');


module.exports = {
    context: path.resolve(__dirname, 'src/main/react'),
    entry: {
        employee: './employee/employee.js',
        department:'./department/department.js'
    },
    devtool: 'sourcemaps',
    cache: true,
    output: { //파일이 생성되는 경로
        path: __dirname,
        filename: './src/main/resources/static/bundle/[name].bundle.js'
    },
    mode:'none',
    module: {
        rules: [ {
            test: /\.js?$/,
            exclude: /(node_modules)/,
            use: {
                loader: 'babel-loader',
                options: {
                    presets: [ '@babel/preset-env', '@babel/preset-react' ]
                }
            }
        },
            {
                test: /\.css$/,
                use: [ 'style-loader', 'css-loader' ]
            },
            {

                test: /\.(png|jpg|jpeg|gif|svg)$/, // 이미지 파일에 대한 규칙
                use: [
                    {
                        loader: 'file-loader',
                        options: {
                            name: '[path][name].[ext]', // 파일 이름 설정
                            context: 'src/main/react', // 소스 경로 설정
                        },
                    },
                ],
            },
            { //이거 없으면 axios 오류
                test: /\.m?js/,
                resolve: {
                    fullySpecified: false
                }
            }
        ]
    },

    plugins: [
        // fix "process is not defined" error:
        new webpack.ProvidePlugin({
            process: 'process/browser',
        }),
    ],
};