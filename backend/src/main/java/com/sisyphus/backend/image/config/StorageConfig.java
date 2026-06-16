package com.sisyphus.backend.image.config;

import com.sisyphus.backend.global.props.AppProps;
import com.sisyphus.backend.global.props.FileProps;
import com.sisyphus.backend.image.service.ImageUrlFactory;
import com.sisyphus.backend.image.service.storage.FileStorageService;
import com.sisyphus.backend.image.service.storage.LocalFileStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FileStorageService 빈을 "무조건" 제공하는 설정.
 * - @Service 스캔 실패나 @Profile 미스매치가 있어도 안전하게 앱이 뜨도록 함.
 */
@Configuration
public class StorageConfig {




    /**
     * FileStorageService 빈이 아직 없다면(Local/Azure 어떤 것도 등록되지 않았다면)
     * 기본으로 LocalFileStorageService를 제공한다.
     *
     * @param fileProps FileProps (role: 업로드 루트 경로, type: FileProps)
     * @param urlFactory ImageUrlFactory (role: public URL 생성, type: ImageUrlFactory)
     * @param appProps AppProps (role: 허용 확장자 / public-base, type: AppProps)
     */
    @Bean
    @ConditionalOnMissingBean(FileStorageService.class)
    public FileStorageService fileStorageService(
            FileProps fileProps,
            ImageUrlFactory urlFactory,
            AppProps appProps
    ) {
        return new LocalFileStorageService(fileProps, urlFactory, appProps);
    }
}
