package com.pangoapi;

import com.pangoapi.domain.entity.advertisement.AdvertisementType;
import com.pangoapi.repository.advertisement.AdvertisementTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * [참고]
 * 본 클래스는 개발(테스트) 시에만 사용됩니다. 실제 운영 시 사용되지 않습니다.
 * 본 클래스의 사용이 끝나면, AdvertisementType 의 @AllArgsConstructor, @NoArgsConstructor(access = AccessLevel.PROTECTED) 어노테이션은 지워져야 합니다.
 */

@RequiredArgsConstructor
@Component
public class PangoApiApplicationDbInitializerForDev {

    private final AdvertisementTypeRepository advertisementTypeRepository;

    @PostConstruct
    public void init() {
        AdvertisementType advertisementType1 = new AdvertisementType(null, "R1", 300L, 250L);
        AdvertisementType advertisementType2 = new AdvertisementType(null, "R2", 300L, 516L);
        AdvertisementType advertisementType3 = new AdvertisementType(null, "R3", 300L, 782L);
        AdvertisementType advertisementType4 = new AdvertisementType(null, "R4", 300L, 1048L);
        AdvertisementType advertisementType5 = new AdvertisementType(null, "R5", 300L, 1314L);
        AdvertisementType advertisementType6 = new AdvertisementType(null, "R6", 300L, 1580L);

        advertisementTypeRepository.save(advertisementType1);
        advertisementTypeRepository.save(advertisementType2);
        advertisementTypeRepository.save(advertisementType3);
        advertisementTypeRepository.save(advertisementType4);
        advertisementTypeRepository.save(advertisementType5);
        advertisementTypeRepository.save(advertisementType6);
    }
}
