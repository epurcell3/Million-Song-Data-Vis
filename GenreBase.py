__author__ = 'Ryan'
import Genre
class GenreBase:
    def __init__(self, songList):
        self.fulllist = {}
        self.zeroslist = []
        notEmpty = True
        while notEmpty:
            currentSong = songList.getNext()
            if currentSong is None:
                notEmpty = False
            else:
                for keyword in currentSong.terms:
                    if self.fulllist[keyword] is None:
                        self.fulllist[keyword] = Genre(keyword, currentSong)
                    else:
                        self.fulllist[keyword].addSong(currentSong)
        for k,v in self.fulllist.items():
            parent = self.findMaxRank(k)
            if parent is not None:
                self.fulllist[parent].children.append(v)
        for gen in self.fulllist.values():
            if gen.rank == 0:
                self.zeroslist.append(gen)

    def findMaxRank(self, keyword):
        current = self.fulllist[keyword]
        maxRank = 0
        maxKey = None
        for key in current.minimalList.values():
            if(self.fulllist[key].rank > maxRank):
                maxRank = self.fulllist[key].rank
                maxKey = key
        return maxKey







