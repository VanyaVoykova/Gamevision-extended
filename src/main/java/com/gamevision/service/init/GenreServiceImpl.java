package com.gamevision.service.init;


import com.gamevision.model.entity.GenreEntity;
import com.gamevision.model.enums.GenreNameEnum;
import com.gamevision.repository.GenreRepository;
import com.gamevision.service.GenreService;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public void initGenres() {
        if (genreRepository.count() == 0) {
            for (GenreNameEnum genreName : GenreNameEnum.values()) {
                GenreEntity genre = new GenreEntity();
                genre.setName(genreName);
                genreRepository.save(genre);
            }
        }
    }
}
