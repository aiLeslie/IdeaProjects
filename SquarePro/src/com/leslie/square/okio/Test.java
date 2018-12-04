package com.leslie.square.okio;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws Exception {
        SquareStream stream = new SquareStream();
        // 从文件读入内容
        stream.readLinesFormFile(new File(".idea/workspace.xml"),
                s -> System.out.println(s)
        );

        // 写入系统环境信息到文件中
        stream.writeEnv(new File(".idea/env.txt"));

        // 从网络中获取数据写入文件中
        HttpUtil.sendOkHttpRequest("http://www.163.com", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("e = " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                System.out.println(string);
                stream.writeLinesToFile(new File(".idea/web.html"), string);
            }
        });

    }
}
